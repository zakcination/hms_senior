-- Add ip_address column to audit_logs table
ALTER TABLE audit_logs 
    ADD COLUMN IF NOT EXISTS ip_address VARCHAR(45); 