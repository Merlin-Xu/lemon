
CREATE TABLE BPM_DELEGATE_INFO(
	ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
	ASSIGNEE VARCHAR(200),
	ATTORNEY VARCHAR(200),
	START_TIME TIMESTAMP,
	END_TIME TIMESTAMP,
	PROCESS_DEFINITION_ID VARCHAR(100),
	STATUS INTEGER,
        CONSTRAINT PK_BPM_DELEGATE_INFO PRIMARY KEY(ID)
);
