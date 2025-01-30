-- Convert entity_id in audit_logs table
ALTER TABLE audit_logs 
    ALTER COLUMN entity_id TYPE UUID 
    USING entity_id::uuid;

-- Convert related_entity_id in notifications table
ALTER TABLE notifications 
    ALTER COLUMN related_entity_id TYPE UUID 
    USING related_entity_id::uuid; 