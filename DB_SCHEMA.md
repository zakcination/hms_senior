### Entity Schemas

#### 1. User Entity

The `User` entity represents all users of the system, including tenants, administrators, housing staff, and maintenance staff.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key for the user. |
| `first_name` | String | User's first name. |
| `last_name` | String | User's last name. |
| `middle_name` | String | User's middle name (optional). |
| `national_id` | String | Unique identifier (e.g., IIN or passport number). |
| `nuid` | String | Nazarbayev University ID. |
| `citizenship` | String | Country of citizenship. |
| `residency_code` | String | Residency country code. |
| `identity_doc_type` | Enum | Type of identity document (`Passport`, `National ID`). |
| `identity_doc_no` | String | Identity document number. |
| `identity_issue_date` | LocalDate | Date the identity document was issued. |
| `email` | String | User's primary email address. |
| `local_phone` | String | Local phone number. |
| `mobile_phone` | String | Mobile phone number. |
| `role_group` | Enum | Primary role group: |
|  |  | - `HOUSING_MANAGEMENT` |
|  |  | - `MAINTENANCE` |
|  |  | - `ADMINISTRATION` |
|  |  | - `SERVICE` |
|  |  | - `TENANT` |
|  |  | - `DEPARTMENT_OF_STUDENT_SERVICES` |
| `role_type` | Enum | Specific role within group: |
|  |  | - HOUSING_MANAGEMENT: (`MANAGER`, `ASSISTANT`, `BLOCK_MANAGER`) |
|  |  | - MAINTENANCE: (`MANAGER`, `STAFF`) |
|  |  | - ADMINISTRATION: (`SENIOR`, `JUNIOR`) |
|  |  | - SERVICE: (`DOMESTIC`) |
|  |  | - TENANT: (`FACULTY`, `STAFF`, `STUDENT`, `RENTER`, `GUEST`) |
|  |  | - DEPARTMENT_OF_STUDENT_SERVICES: (`MANAGER`, `ASSISTANT`) |
| `position` | String | Job title (e.g., `Vice-President`, `Cleaner`). |
| `department` | String | Department or school the user belongs to. |
| `family_members` | JSON/Array | Family members residing with the user. |
| `domestic_staff` | JSON/Array | Household staff (e.g., nannies, drivers). |
| `vehicles` | JSON/Array | Details of vehicles owned by the user. |

**Note**: The role structure follows a two-level hierarchy:

1. `role_group`: Defines the primary organizational group
2. `role_type`: Specifies the exact role within that group

---

#### 2. Property Entity

Represents all properties managed by the system.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key for the property. |
| `property_type` | Enum | Type of block (`HV_Apartment`, `Campus_Apartment`, `NL_Apartment`,`Townhouse`, `Cottage`, `Dormitory`). |
| `property_name` | String | Block name or number (e.g., `Block 38`, `Highvill RC`). |
| `room_type` | Enum | Type of room (`TWO_BEDDED_ROOM`, `THREE_BEDDED_ROOM`, `FOUR_BEDDED_ROOM`, `STUDIO`, `ONE_BEDROOM`, `TWO_BEDROOM`, `THREE_BEDROOM`, `FOUR_BEDROOM`). |
| `area` | Double | Total area of the property in square meters. |
| `status` | Enum | Current status (`Occupied`, `Reserved`, `Vacant`, `Out of Service`). |
| `monthly_rent` | Double | Monthly rent for the property. |
| `allowed_tenant_types` | JSON/Array | List of tenant types allowed (`FACULTY`, `STAFF`, `STUDENT`, `RENTER`, `GUEST`). |
| `description` | String | Additional property details. |

**Property Access Rules**:

1. Dormitory:

- Allowed tenant types: `STUDENT` only
- Exception: Department of Student Services staff can manage

2. HV_Apartment:

- Allowed tenant types: `FACULTY`, `STAFF`
- Exception: Housing Management staff can manage

3. Campus_Apartment:

- Allowed tenant types: `FACULTY`, `STAFF`
- Exception: Housing Management staff can manage

4. NL_Apartment:

- Allowed tenant types: `FACULTY`, `STAFF`
- Exception: Housing Management staff can manage

5. Townhouse:

- Allowed tenant types: `FACULTY`
- Exception: Housing Management staff can manage

6. Cottage:

- Allowed tenant types: `FACULTY`
- Exception: Housing Management staff can manage

**Note**: Access control is enforced through:

1. User's `role_type` under the `TENANT` group
2. Property's `allowed_tenant_types`
3. Special access granted to management roles regardless of tenant type

---

#### 3. Lease Entity

Tracks agreements between users and properties.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key for the lease. |
| `contract_number` | String | Unique identifier for the contract. |
| `user_id` | Foreign Key | Link to the `User` entity. |
| `property_id` | Foreign Key | Link to the `Property` entity. |
| `check_in_date` | LocalDate | Start date of the lease. |
| `check_out_date` | LocalDate | End date of the lease. |
| `status` | Enum | Lease status (`Active`, `Completed`, `Cancelled`, `Resettled`). |
| `reservation_status` | Boolean | Indicates if the property is reserved. |
| `penalties` | Double | Total penalty amount. |
| `deposit` | Double | Security deposit paid. |
| `payments` | JSON/Array | List of payments related to the lease. |
| `resettlement_history` | JSON/Array | History of resettlements. |

---
According to property Id there different scenarios, to be filled in, since some properties are :

- Dormitory | Shared by multiple tenants | Reserved by 2 Students | No Deposit | No Penalties
- Apartment | Not Shared | Reserved by 1 User | Deposit
- Townhouse | Not Shared | Reserved by 1 User | Deposit
- Cottage | Not Shared | Reserved by 1 User | Deposit

#### 4. Payment Entity

Tracks all payments, including living payments, deposits, and utility bills.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key for the payment. |
| `payment_type` | Enum | Type of payment (`Deposit`, `Dorm Payment`, `Utility Payment`). |
| `amount` | Double | Total amount paid. |
| `status` | Enum | Payment status (`Pending`, `Completed`, `In Process`). |
| `payment_date` | LocalDate | Date the payment was made. |
| `due_date` | LocalDate | Due date for the payment. |
| `user_id` | Foreign Key | Link to the `User` entity. |
| `property_id` | Foreign Key | Link to the `Property` entity. |
| `discount` | Double | Discount applied (e.g., 20% for students paying to the student fund). |
| `tariff_rate` | Double | Tariff rate used for calculation. |

---

#### 5. Maintenance Request

**Purpose**: Tracks service requests for property maintenance.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key for the maintenance request. |
| `request_type` | Enum | Type of maintenance (`Plumbing`, `Internet`, `General Repairs`, etc.). |
| `description` | String | Detailed description of the issue. |
| `status` | Enum | Status of the request (`Pending`, `In Progress`, `Completed`). |
| `priority` | Enum | Priority of the request (`Low`, `Medium`, `High`, `Critical`). |
| `request_date` | LocalDateTime | Date and time of the request. |
| `completion_date` | LocalDateTime | Completion date (if applicable). |
| `user_id` | Foreign Key | Link to the `User` entity. |
| `property_id` | Foreign Key | Link to the `Property` entity. |
| `assigned_staff` | JSON/Array | List of assigned maintenance staff. |
| `cost` | Double | Total cost of the maintenance service. |

---

#### 6. Pricing Policy

**Purpose**: Defines rent and utility rates.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key for the pricing policy. |
| `property_type` | Enum | Type of property (`Dormitory`, `Apartment`, `Townhouse`, `Cottage`). |
| `room_type` | Enum | Type of room (`TWO_BEDDED_ROOM`, `THREE_BEDDED_ROOM`, `FOUR_BEDDED_ROOM`, `STUDIO`, `ONE_BEDROOM`, `TWO_BEDROOM`, `THREE_BEDROOM`, `FOUR_BEDROOM`). |
| `daily_rate` | Double | Daily rate for the property. |
| `monthly_rate_with_utilities` | Double | Monthly rate including utilities. |
| `monthly_rate_without_utilities` | Double | Monthly rate excluding utilities. |
| `has_student_fund_discount` | Boolean | Whether the 20% student fund discount is applicable (only for Dormitory type). |
| `student_fund_discount_rate` | Double | Fixed 20% discount rate for students paying to student fund (only for Dormitory type). |
| `other_discount_rate` | Double | Optional discount rate for other scenarios. |
| `effective_from` | LocalDate | Start date for the policy. |
| `effective_to` | LocalDate | End date for the policy. |

**Note**: Discount Rules

1. For Dormitory properties:

- If `has_student_fund_discount` is true and student pays to student fund:
  - Apply fixed 20% discount (`student_fund_discount_rate`)
- Other discounts may apply through `other_discount_rate`

2. For non-Dormitory properties:

- Only `other_discount_rate` may apply

---

#### 7. Notification Entity

| Field | Type | Description |
|-------|------|-------------|
| `id` | UUID (Primary Key) | Unique identifier for each notification. |
| `user_id` | UUID (Foreign Key) | The recipient of the notification. |
| `related_entity_id` | UUID (Nullable) | ID of the related entity (Lease, Payment, Incident). |
| `entity_type` | Enum (Nullable) | Specifies which entity the notification refers to. |
| `message` | TEXT | Content of the notification. |
| `status` | Enum | Status (`UNREAD`, `READ`, `DISMISSED`). |
| `notification_type` | Enum | Type (`INFO`, `WARNING`, `CRITICAL`). |
| `created_at` | TIMESTAMP | Time when the notification was generated. |
| `read_at` | TIMESTAMP (Nullable) | Time when the notification was read. |
| `deleted_at` | TIMESTAMP (Nullable) | Time when the notification was dismissed. |

---

#### 8. Audit Logs

**Purpose**: Tracks all changes and actions in the system for security, compliance, and debugging.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key. |
| `entity_type` | String | Entity affected (e.g., `User`, `Lease`). |
| `entity_id` | Integer | ID of the affected entity. |
| `action_type` | Enum | Action performed (`Create`, `Update`, `Delete`). |
| `performed_by` | Foreign Key | Link to the `User` who performed the action. |
| `timestamp` | LocalDateTime | Date and time of the action. |
| `details` | String | Additional details about the action. |

---

#### 9. Feedback

**Purpose**: Collects feedback from tenants, staff, or administrators for service improvements.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key. |
| `user_id` | Foreign Key | Link to the `User` providing feedback. |
| `property_id` | Foreign Key | Link to the `Property` related to feedback (optional). |
| `feedback_type` | Enum | Type of feedback (`Service`, `System`, `Property`). |
| `message` | String | Feedback content. |
| `submitted_at` | LocalDateTime | Timestamp of submission. |
| `status` | Enum | Status (`Pending`, `Reviewed`, `Resolved`). |

---

#### 10.  Visitor Management

**Purpose**: Tracks guests visiting properties for security and reporting.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key. |
| `user_id` | Foreign Key | Link to the `User` hosting the visitor. |
| `visitor_name` | String | Name of the visitor. |
| `visit_date` | LocalDateTime | Date and time of the visit. |
| `visit_purpose` | String | Reason for the visit. |
| `approved_by` | Foreign Key | Link to the `Admin` who approved the visit (optional). |

---

#### 11. Emergency Contacts

**Purpose**: Manages emergency contacts for tenants or property issues.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key. |
| `contact_name` | String | Name of the emergency contact. |
| `Description` | String | Description of the emergency contact. |
| `contact_number` | String | Emergency contact's phone number. |

---

### Entity Relationships

#### ER1. User-Lease Relationship

- **Scenario**: A user can have multiple leases over time, reflecting their history of residence.
- **Cardinality**: One-to-Many
- **Use Case**:
  - A student checks into a dorm room and generates a lease for the semester.

---

#### ER2. Lease-Property Relationship

- **Scenario**: Each lease is associated with one property, but a property can have multiple leases over time.
- **Cardinality**: Many-to-One
- **Use Case**:
  - A single apartment can be leased to different users over time.
  - A townhouse has leases for various tenants during different periods.
  - Resettlement updates the lease to reference a new property.

---

#### 3. User ↔ Property

- **Scenario**: A property may have an assigned user (tenant or staff) at a given time.
- **Cardinality**: One-to-One
- **Use Case**:
  - A student is assigned to a dorm room.
  - A staff member occupies a townhouse or apartment for official duties.

---

#### 4. User ↔ Payment

- **Scenario**: A user is responsible for multiple payments, including rent, deposits, and utility bills.
- **Cardinality**: One-to-Many
- **Use Case**:
  - A tenant makes monthly payments for rent and utilities.
  - A user's payment history tracks overdue, completed, or pending payments.
  - Discounts (e.g., student fund discount) are applied to specific users.

---

#### 5. Lease ↔ Payment

- **Scenario**: Each lease generates related payments, such as deposits, rent, and penalties.
- **Cardinality**: One-to-Many
- **Use Case**:
  - Payments are automatically linked to the lease they belong to.
  - Lease termination triggers a refund of the security deposit or a penalty payment.

---

#### 6. User ↔ Maintenance Request

- **Scenario**: Users can submit maintenance requests for properties they occupy.
- **Cardinality**: One-to-Many
- **Use Case**:
  - A student submits a plumbing request for their dorm room.
  - A renter raises an issue about internet services in their apartment.
  - Users can track the status of their submitted requests.

---

#### 7. Property ↔ Maintenance Request

- **Scenario**: Maintenance requests are tied to specific properties.
- **Cardinality**: One-to-Many
- **Use Case**:
  - Maintenance staff address issues specific to a property (e.g., a blocked pipe or HVAC repair).
  - Track property-level maintenance history for reporting and analytics.

---

#### 8. User (Maintenance Staff) ↔ Maintenance Request

- **Scenario**: Maintenance requests are assigned to one or more staff members.
- **Cardinality**: Many-to-Many
- **Use Case**:
  - A team of staff members handles a critical repair.
  - Individual staff members track assigned tasks for completion.
  - Feedback is collected to evaluate staff performance.

---

#### 9. User ↔ Notifications

- **Scenario**: Notifications are sent to users for system events like rent reminders, lease expiry, or maintenance updates.
- **Cardinality**: One-to-Many
- **Use Case**:
  - A student receives a notification about their rent due date.
  - A renter is alerted about their lease nearing expiry.
  - A user gets updates on their maintenance request status.

---

#### 10. Property ↔ PricingPolicy

- **Scenario**: Pricing policies define rates for properties based on type and room configuration.
- **Cardinality**: Many-to-One
- **Use Case**:
  - A dorm room's rent is calculated based on the current pricing policy.
  - An apartment has a different monthly rate with or without utilities.
  - Pricing policies include discounts, such as 20% for students paying into the student fund.

---

#### 11. User ↔ Vehicles

- **Scenario**: Users may have one or more vehicles associated with their account.
- **Cardinality**: One-to-Many
- **Use Case**:
  - A tenant registers their vehicle for parking in their assigned block.
  - Multiple vehicles are managed for family members of a user.

---

#### 12. User ↔  User (Domestic Staff)

- **Scenario**: Users may employ domestic staff for personal or property-related work.
- **Cardinality**: One-to-Many
- **Use Case**:
  - A family residing in a townhouse employs a nanny or driver.
  - Domestic staff details are linked to the user account.

---

#### 13. Lease ↔ Notifications

- **Scenario**: Lease-related events trigger notifications to users.
- **Cardinality**: One-to-Many
- **Use Case**:
  - Notifications are sent for lease expiry reminders.
  - Lease resettlement updates trigger a notification about the new property assignment.

---

#### 14. PricingPolicy ↔ Payment

- **Scenario**: Payments are calculated based on the active pricing policy.
- **Cardinality**: Many-to-One
- **Use Case**:
  - Rent for a property is determined by the corresponding daily or monthly rate.
  - Discounts are applied based on pricing policy rules.

---

#### 15. User ↔ Feedback

- **Scenario**: Users provide feedback about their experience with the system, property, or services.
- **Cardinality**: **1:N**
  - A user can provide multiple feedback entries over time.
- **Use Case**:
  - A tenant submits feedback about a delayed maintenance request.
  - Feedback can be tied to specific properties or services.
- **Relationship**:
  - `Feedback.user_id` → `User.id`
  - `Feedback.property_id` → `Property.id` (optional)

---

#### 22. **User ↔ Visitor Management**

- **Scenario**: Users can host visitors, and each visitor is logged for security and reporting purposes.
- **Cardinality**: **1:N**
  - A user can host multiple visitors.
- **Use Case**:
  - A tenant registers a family member as a visitor for the weekend.
  - The system tracks the visit's purpose and duration.
- **Relationship**:
  - `Visitor.user_id` → `User.id`

---

### Key Relationship Diagram

Here's a conceptual summary of the relationships:

- **User ↔ Lease** (1:N)
- **Lease ↔ Property** (M:1)
- **User ↔ Property** (1:1)
- **User ↔ Payment** (1:N)
- **Lease ↔ Payment** (1:N)
- **User ↔ MaintenanceRequest** (1:N)
- **Property ↔ MaintenanceRequest** (1:N)
- **MaintenanceStaff ↔ MaintenanceRequest** (M:N)
- **User ↔ Notifications** (1:N)
- **Property ↔ PricingPolicy** (M:1)
- **PricingPolicy ↔ Payment** (M:1)
- **User ↔ Vehicles** (1:N)
- **User ↔ DomesticStaff** (1:N)
- **Lease ↔ Notifications** (1:N)
- **User ↔ Feedback** (1:N)
- **User ↔ Visitor Management** (1:N)
- **User ↔ Emergency Contacts** (1:N)

---

### System Operations

#### Op1. User Management

**Description**: Handles tenant, staff, and admin information, including roles and personal details.
**Operations**:

- **Create User**: Add a new user with role and contact details.
- **View User**: Retrieve user information by ID or role.
- **Update User**: Modify user details (e.g., role change, contact updates).
- **Delete User**: Deactivate or remove a user.
- **List Users**: Filter users by roles, departments, or properties.
- **Authenticate**: Login and manage sessions for users.

#### Op2. Property Management

**Description**: Manages details of all properties, including blocks, room types, and statuses.
**Operations**:

- **Add Property**: Create a new property with type, area, and block details.
- **Update Property**: Modify property attributes (e.g., status, room type).
- **View Property**: Retrieve details of a specific property.
- **List Properties**: Filter properties by type, availability, or block.
- **Delete Property**: Remove properties no longer in use.

#### Op3. Lease Management

**Description**: Manages contracts between users and properties, including check-in and check-out.
**Operations**:

- **Create Lease**: Generate a lease with user, property, and contract details.
- **View Lease**: Fetch lease details by ID, user, or property.
- **Update Lease**: Modify lease attributes (e.g., extension, resettlement).
- **Terminate Lease**: Handle lease completion or cancellation.
- **List Leases**: Filter leases by user, property, or active status.

#### Op4. Payment Management

**Description**: Handles rent, deposits, and utility payments for properties and users.
**Operations**:

- **Initiate Payment**: Create a new payment linked to a user and lease.
- **View Payment**: Retrieve payment details by user, lease, or type.
- **Update Payment**: Modify payment status (e.g., from pending to completed).
- **Refund Payment**: Process refunds for deposits or overpayments.
- **List Payments**: Filter payments by user, property, or payment type.

#### Op5. Maintenance Request Management

**Description**: Manages service requests for property maintenance.
**Operations**:

- **Create Request**: Log a maintenance request with description and priority.
- **View Request**: Fetch details of a specific request.
- **Update Request**: Modify request status (e.g., from pending to resolved).
- **Assign Staff**: Allocate maintenance staff to handle requests.
- **List Requests**: Filter requests by property, status, or priority.

#### Op6. Notification Management

**Description**: Sends alerts and reminders to users about system events.
**Operations**:

- **Create Notification**: Generate a new notification for a user or event.
- **View Notifications**: Retrieve all notifications for a specific user.
- **Mark as Read**: Update notification status to read or dismissed.
- **Delete Notification**: Remove outdated or irrelevant notifications.

#### Op7. Pricing Policy Management

**Description**: Defines rent and utility rates for properties.
**Operations**:

- **Create Policy**: Add a new pricing policy with daily/monthly rates.
- **View Policy**: Retrieve pricing details for a specific property or type.
- **Update Policy**: Modify rates or discounts in an existing policy.
- **Delete Policy**: Archive policies that are no longer effective.
- **Apply Policy**: Automatically calculate rent based on the policy.

#### Op8. Incident Management

**Description**: Tracks and manages issues like security breaches or emergencies.
**Operations**:

- **Report Incident**: Log a new incident with description and type.
- **View Incident**: Retrieve details of a specific incident.
- **Update Incident**: Modify incident status (e.g., in progress, resolved).
- **Assign Staff**: Allocate staff to handle the incident.
- **List Incidents**: Filter incidents by type, property, or status.

#### Op9. Vehicle Management

**Description**: Manages vehicles registered to users for parking.
**Operations**:

- **Register Vehicle**: Add a new vehicle for a user.
- **View Vehicles**: List vehicles for a user or property.
- **Update Vehicle**: Modify vehicle details (e.g., parking slot).
- **Delete Vehicle**: Remove a vehicle from the system.

#### Op10. Domestic Staff Management

**Description**: Tracks household staff employed by tenants.
**Operations**:

- **Add Staff**: Register a new domestic staff member.
- **View Staff**: Retrieve staff details for a specific user.
- **Update Staff**: Modify staff roles or contracts.
- **Remove Staff**: Terminate staff association with a user.

#### Op11. Search and Filter

**Description**: Applies dynamic filters to quickly retrieve records.
**Operations**:

- **Search**: Enter keywords or filters to find specific records.
- **Filter**: Apply predefined or custom filters to narrow down results.

#### Op12. Audit Logs

**Description**: Maintains history of changes for accountability.
**Operations**:

- **View Logs**: Access a log of all system changes.
- **Search Logs**: Enter keywords or filters to find specific log entries.

#### Op13. Permissions

**Description**: Enforces role-based access control (RBAC) for sensitive operations.
**Operations**:

- **Assign Roles**: Assign roles to users based on their responsibilities.
- **Manage Permissions**: Modify user permissions for specific operations.

#### Op14. Batch Processing

**Description**: Supports bulk actions for operations like payments or notifications.
**Operations**:

- **Process Payments**: Batch process multiple payments at once.
- **Send Notifications**: Batch send notifications to multiple users.

## Suggested Upgrades to Database Schema

Here are some additional entities that may not have been explicitly covered in the current **Housing Management System (HMS)** design but can enhance functionality, scalability, and user satisfaction:

---

#### 1. Role Entity

**Purpose**: Defines base roles in the system.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key for the role. |
| `name` | String | Name of the role. |
| `description` | String | Description of role responsibilities. |
| `is_active` | Boolean | Whether the role is currently active. |
| `created_at` | LocalDateTime | When the role was created. |
| `updated_at` | LocalDateTime | When the role was last updated. |

#### 2. Role Group Entity

**Purpose**: Groups roles for organizational hierarchy.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key for the role group. |
| `name` | String | Name of the group (`Housing`, `Maintenance`, `Administration`, `Service`). |
| `description` | String | Description of the group's purpose. |
| `parent_group_id` | Integer | Optional reference to parent group for hierarchy. |

#### 3. Permission Entity

**Purpose**: Defines granular permissions for system operations.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key for the permission. |
| `name` | String | Name of the permission. |
| `description` | String | Description of what the permission allows. |
| `resource` | String | Resource this permission applies to. |
| `action` | Enum | Action type (`CREATE`, `READ`, `UPDATE`, `DELETE`). |

#### 4. Role Permission Entity

**Purpose**: Maps roles to permissions.

| Field | Type | Description |
|-------|------|-------------|
| `id` | Integer | Primary Key. |
| `role_id` | Foreign Key | Reference to Role. |
| `permission_id` | Foreign Key | Reference to Permission. |

### Role Hierarchies and Permissions

#### Housing Management Group

- **Manager**
- Full access to property management
- Oversee assistants and block managers
- Approve major housing decisions
- **Assistant**
- Day-to-day property operations
- Handle tenant requests
- Generate reports
- **Block Manager**
- Manage specific blocks/properties
- Handle block-specific maintenance requests
- Monitor block occupancy

#### Maintenance Group

- **Manager**
- Oversee all maintenance operations
- Assign staff to requests
- Approve maintenance budgets
- **Staff**
- Execute maintenance tasks
- Update request status
- Report issues

#### Administration Group

- **Senior**
- System-wide administration
- Policy management
- User management
- **Junior**
- Basic administrative tasks
- Data entry
- Report generation

#### Service Group

- **Domestic Service**
- Access to assigned properties
- Service schedule management
- **Student Service**
- Limited access to student facilities
- Basic service operations

#### Tenant Group

- **Faculty**
- Extended stay privileges
- Family accommodation options
- **Staff**
- Standard housing access
- Basic maintenance requests
- **Student**
- Dormitory access
- Limited maintenance requests
- **Renter**
- Standard tenant privileges
- **Guest**
- Temporary access
- Limited services

#### Department of Student Services Group

- **Manager**
- Oversee student housing operations
- Manage student accommodation policies
- Handle escalated student issues
- Coordinate with other departments
- Approve special accommodation requests
- Generate comprehensive reports
- Manage student service staff

- **Assistant**
- Process student housing applications
- Handle day-to-day student inquiries
- Maintain student housing records
- Coordinate student check-ins/check-outs
- Support room allocation process
- Generate routine reports
- Assist with student welfare concerns

---
