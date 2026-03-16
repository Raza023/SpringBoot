--It is used to set auto increment start from 3 for new insertion.
ALTER TABLE Users ALTER COLUMN id RESTART WITH 3;

--Reset Posts ID to start at 4.
ALTER TABLE Posts ALTER COLUMN ID RESTART WITH 4;