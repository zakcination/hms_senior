-- First, add a temporary column
ALTER TABLE notifications ADD COLUMN related_entity_id_new UUID;

-- Convert the existing data to UUID
UPDATE notifications SET related_entity_id_new = related_entity_id::uuid WHERE related_entity_id IS NOT NULL;

-- Drop the old column
ALTER TABLE notifications DROP COLUMN related_entity_id;

-- Rename the new column to the original name
ALTER TABLE notifications RENAME COLUMN related_entity_id_new TO related_entity_id; 