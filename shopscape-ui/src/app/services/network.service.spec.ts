import { NetworkService } from './network.service';
import {TestBed} from '@angular/core/testing';

describe('NetworkService', () => {
  let service: NetworkService
  let fetchSpy: jasmine.Spy;

  beforeEach(() => {
    fetchSpy = spyOn(window, 'fetch').and.returnValue(Promise.resolve({ ok: true, json: () => Promise.resolve({}) } as Response));
    TestBed.configureTestingModule({
      providers: [
        NetworkService,
        { provide: 'fetch', useValue: fetchSpy }
      ]
    });
    // @ts-ignore
    service = TestBed.inject(NetworkService);
  });

  it('should call fetch with GET method', async () => {
    await service.get('https://example.com');
    expect(fetchSpy).toHaveBeenCalledWith('https://example.com', { method: 'GET' });
  });

  it('should call fetch with GET method and headers', async () => {
    const headers = new Headers();
    headers.set('Authorization', 'Bearer token');
    await service.get('https://example.com', headers);
    expect(fetchSpy).toHaveBeenCalledWith('https://example.com', { method: 'GET', headers });
  });

  it('should call fetch with POST method', async () => {
    const data = { key: 'value' };
    await service.post('https://example.com', data);
    expect(fetchSpy).toHaveBeenCalledWith('https://example.com', { method: 'POST', body: '{"key":"value"}' });
  });

  it('should call fetch with POST method and headers', async () => {
    const headers = new Headers();
    headers.set('Content-Type', 'application/json');
    const data = { key: 'value' };
    await service.post('https://example.com', data, headers);
    expect(fetchSpy).toHaveBeenCalledWith('https://example.com', { method: 'POST', headers, body: '{"key":"value"}' });
  });

  it('should call fetch with PUT method', async () => {
    const data = { key: 'value' };
    await service.put('https://example.com', data);
    expect(fetchSpy).toHaveBeenCalledWith('https://example.com', { method: 'PUT', body: '{"key":"value"}' });
  });

  it('should call fetch with PUT method and headers', async () => {
    const headers = new Headers();
    headers.set('Content-Type', 'application/json');
    const data = { key: 'value' };
    await service.put('https://example.com', data, headers);
    expect(fetchSpy).toHaveBeenCalledWith('https://example.com', { method: 'PUT', headers, body: '{"key":"value"}' });
  });

  it('should call fetch with DELETE method', async () => {
    await service.delete('https://example.com');
    expect(fetchSpy).toHaveBeenCalledWith('https://example.com', { method: 'DELETE' });
  });

  it('should call fetch with DELETE method and headers', async () => {
    const headers = new Headers();
    headers.set('Authorization', 'Bearer token');
    await service.delete('https://example.com', headers);
    expect(fetchSpy).toHaveBeenCalledWith('https://example.com', { method: 'DELETE', headers });
  });
});
