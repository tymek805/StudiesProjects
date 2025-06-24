CREATE TABLE DimLocation (
    LocationId INT IDENTITY(1,1) PRIMARY KEY,
    City VARCHAR(100),
    Region VARCHAR(50),
    Country VARCHAR(50)
);

CREATE TABLE DimResult (
    ResultId INT IDENTITY(1,1) PRIMARY KEY,
    Suicide BIT NOT NULL,
    Successful BIT NOT NULL
);

CREATE TABLE DimWeapon (
    WeaponId INT IDENTITY(1,1) PRIMARY KEY,
    WeaponType VARCHAR(100) NOT NULL
);

CREATE TABLE DimTerroristGroup (
    GroupId INT IDENTITY(1,1) PRIMARY KEY,
    GroupName VARCHAR(150) NOT NULL
);

CREATE TABLE DimAttackType (
    AttackTypeId INT IDENTITY(1,1) PRIMARY KEY,
    AttackType VARCHAR(50) NOT NULL
);

CREATE TABLE DimDate (
    DateId INT IDENTITY(1,1) PRIMARY KEY,
    FullAttackDate DATE,
    DayNumberOfMonth INT NOT NULL,
    MonthNumberOfYear INT NOT NULL,
    CalendarYear INT NOT NULL
);

CREATE TABLE FactAttack (
    AttackId INT IDENTITY(1,1) PRIMARY KEY,
    VictimsKilled INT NOT NULL,
    VictimsWounded INT NOT NULL,
    LocationId INT,
    ResultId INT,
    WeaponId INT,
    GroupId INT,
    AttackTypeId INT,
    DateId INT,

    CONSTRAINT FK_DimLocation FOREIGN KEY (LocationId) REFERENCES DimLocation(LocationId),
    CONSTRAINT FK_DimResult FOREIGN KEY (ResultId) REFERENCES DimResult(ResultId),
    CONSTRAINT FK_DimWeapon FOREIGN KEY (WeaponId) REFERENCES DimWeapon(WeaponId),
    CONSTRAINT FK_DimTerroristGroup FOREIGN KEY (GroupId) REFERENCES DimTerroristGroup(GroupId),
    CONSTRAINT FK_DimAttackType FOREIGN KEY (AttackTypeId) REFERENCES DimAttackType(AttackTypeId),
    CONSTRAINT FK_DimDate FOREIGN KEY (DateId) REFERENCES DimDate(DateId)
)