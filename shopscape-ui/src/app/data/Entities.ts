import { ShopScapeIssue } from './ShopScapeIssue';
import { ShopScapeRequest } from './ShopScapeRequest';


export class MainEntity {
  id: string;
  name: string;
  entityType: EntityType;
  requests: ShopScapeRequest[] = [];
  issues: ShopScapeIssue[] = [];

  constructor(id: string, name: string, entityType: EntityType) {
    this.id = id;
    this.name = name;
    this.entityType = entityType;
  }
}

export class ShopScapeStore extends MainEntity {
  streetAddress: string;
  city: string;
  state: string;
  zipCode: string;
  phoneNumber: string;
  equipment: Equipment[] = [];

  constructor(
    id: string,
    name: string,
    streetAddress: string,
    city: string,
    state: string,
    zipCode: string,
    phoneNumber: string
  ) {
    super(id, name, EntityType.Shop); // Assuming entityType 1 for Store
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.phoneNumber = phoneNumber;
  }
}

export class Equipment extends MainEntity {
  serialNumber: string | null;
  description: string | null;
  modelNumber: string | null;

  constructor(
    id: string,
    name: string,
    serialNumber: string | null,
    description: string | null,
    modelNumber: string | null
  ) {
    super(id, name, EntityType.Equipment); // Assuming entityType 2 for Equipment
    this.serialNumber = serialNumber;
    this.description = description;
    this.modelNumber = modelNumber;
  }
}

export class StoreSummary {
  Name: string;
  ID: string;
  IssueCount: number;
  RequestCount: number;

  constructor(name: string, id: string, issueCount: number, requestCount: number) {
    this.Name = name;
    this.ID = id;
    this.IssueCount = issueCount;
    this.RequestCount = requestCount;
  }
}

enum EntityType {
  Shop = 1,
  Equipment = 2,
}
