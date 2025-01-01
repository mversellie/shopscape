import {Equipment, ShopScapeStore} from '../data/Entities';
import {ShopScapeIssue} from '../data/ShopScapeIssue';
import {ShopScapeRequest} from '../data/ShopScapeRequest';

export function makeShopResponse():ShopScapeStore[]{
  let mockShops: ShopScapeStore[] = [
    new ShopScapeStore(
      '1',
      'Shop A',
      '123 Main St',
      'Anytown',
      'CA',
      '90210',
      '555-123-4567'
    ),
    new ShopScapeStore(
      '2',
      'Shop B',
      '456 Oak Ave',
      'Springfield',
      'IL',
      '62701',
      '555-987-6543'
    )
  ];

  // Create sample requests and issues for Shop A
  const request1 = new ShopScapeRequest('1', 'Request 1 description', '1');
  const request2 = new ShopScapeRequest('2', 'Request 2 description', '1');
  const issue1 = new ShopScapeIssue('1', 'Issue 1 description', '1');

  // Add requests and issues to Shop A
  mockShops[0].requests = [request1, request2];
  mockShops[0].issues = [issue1];

  // Create sample equipment for Shop A
  const equipment1 = new Equipment('101', 'Equipment 1', 'EQ12345', 'Description for Equipment 1', 'Model X');
  const equipment2 = new Equipment('102', 'Equipment 2', 'EQ67890', 'Description for Equipment 2', 'Model Y');

  // Create requests and issues for equipment
  const equipmentRequest1 = new ShopScapeRequest('11', 'Request for Equipment 1', '101');
  const equipmentIssue1 = new ShopScapeIssue('11', 'Issue with Equipment 1', '101');
  const equipmentRequest2 = new ShopScapeRequest('12', 'Request for Equipment 2', '102');

  // Add requests and issues to equipment
  equipment1.requests = [equipmentRequest1];
  equipment1.issues = [equipmentIssue1];
  equipment2.requests = [equipmentRequest2];

  mockShops[0].equipment = [equipment1, equipment2];
  return mockShops
}
