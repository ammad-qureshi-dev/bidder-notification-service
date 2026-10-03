-- Adds LEADING_BID to the NotificationSubject and TemplateName enum CHECK constraints
-- on notifications_service.notification.
--
-- Hibernate generates CHECK constraints for @Enumerated(EnumType.STRING) columns, but
-- ddl-auto=update does not refresh them when new enum values are added.
--
-- Verify constraint names first if needed:
--   SELECT conname, pg_get_constraintdef(oid)
--   FROM pg_constraint
--   WHERE conrelid = 'notifications_service.notification'::regclass AND contype = 'c';

BEGIN;

ALTER TABLE notifications_service.notification
    DROP CONSTRAINT IF EXISTS notification_subject_check;

ALTER TABLE notifications_service.notification
    ADD CONSTRAINT notification_subject_check CHECK (subject IN (
        'SETUP_CONTACT_METHOD',
        'VERIFY_ACCOUNT',
        'WELCOME_TO_BIDDER',
        'ACCOUNT_VERIFIED',
        'PASSWORD_RESET_LINK_SENT',
        'PASSWORD_UPDATED',
        'BID_REQUEST_ACCEPTED',
        'BID_REQUEST_REJECTED',
        'BID_REQUEST_UPDATED',
        'BID_REQUEST_SENT',
        'AUCTION_CLOSED',
        'AUCTION_LIVE',
        'AUCTION_PAUSED',
        'LEADING_BID'
    ));

ALTER TABLE notifications_service.notification
    DROP CONSTRAINT IF EXISTS notification_template_check;

ALTER TABLE notifications_service.notification
    ADD CONSTRAINT notification_template_check CHECK (template IN (
        'CONTACT_METHOD_SETUP',
        'PASSWORD_UPDATED',
        'PASSWORD_RESET_LINK_SENT',
        'ACCOUNT_VERIFIED',
        'BID_REQUEST_ACCEPTED',
        'BID_REQUEST_REJECTED',
        'BID_REQUEST_UPDATED',
        'BID_REQUEST_SENT',
        'AUCTION_CLOSED',
        'AUCTION_LIVE',
        'AUCTION_PAUSED',
        'WELCOME_REGISTRATION',
        'ACCOUNT_VERIFICATION',
        'LEADING_BID'
    ));

COMMIT;
