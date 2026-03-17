INSERT INTO public.transactions (status, amount, currency, transaction_date, created_at, updated_at, sender_id, receiver_id)
VALUES
    ('PENDING', 15000, 'USD', '2026-03-01 10:15:00', NOW(), NOW(), 'USR1001', 'USR2001'),
    ('SUCCESS', 25000, 'EUR', '2026-03-02 14:30:00', NOW(), NOW(), 'USR1002', 'USR2002'),
    ('FAILED', 5000, 'GBP', '2026-03-03 09:45:00', NOW(), NOW(), 'USR1003', 'USR2003'),
    ('PENDING', 120000, 'USD', '2026-03-04 16:20:00', NOW(), NOW(), 'USR1004', 'USR2004'),
    ('SUCCESS', 75000, 'JPY', '2026-03-05 11:10:00', NOW(), NOW(), 'USR1005', 'USR2005'),
    ('SUCCESS', 30000, 'USD', '2026-03-06 08:55:00', NOW(), NOW(), 'USR1006', 'USR2006'),
    ('PENDING', 45000, 'EUR', '2026-03-06 19:40:00', NOW(), NOW(), 'USR1007', 'USR2007'),
    ('SUCCESS', 99000, 'USD', '2026-03-07 13:25:00', NOW(), NOW(), 'USR1008', 'USR2008'),
    ('FAILED', 2000, 'GBP', '2026-03-07 21:15:00', NOW(), NOW(), 'USR1009', 'USR2009'),
    ('SUCCESS', 64000, 'USD', '2026-03-08 07:50:00', NOW(), NOW(), 'USR1010', 'USR2010');