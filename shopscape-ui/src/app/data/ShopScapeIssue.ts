export class ShopScapeIssue {
  public id: string;
  public name: string;
  public description: string;
  public entityId: string;
  public status: string;

  constructor(
    id: string = '',
    name: string = '',
    description: string = '',
    entityId: string = '',
    status: string = 'Invalid'
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.entityId = entityId;
    this.status = status;
  }
}
