import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NetworkService {

  constructor() { }

  async get(url: string, headers?: Headers): Promise<Response> {
    if(typeof headers !== 'undefined') {
      return fetch(url,{
        method: 'GET',
        headers:headers})
    }

    return fetch(url,{method: 'GET'})
    };

  async post(url: string, data: any, headers?: Headers): Promise<Response> {
    if(typeof headers !== 'undefined') {
      return fetch(url, {
        method: 'POST',
        headers:headers,
        body: JSON.stringify(data)
      });
    }
    return fetch(url, {
      method: 'POST',
      body: JSON.stringify(data)
    });
  }

  async put(url: string, data: any, headers?: Headers): Promise<Response> {
    if(typeof headers !== 'undefined'){
      return fetch(url, {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify(data)
      });
    }
    return fetch(url, {
      method: 'PUT',
      body: JSON.stringify(data)
    });
  }

  async delete(url: string, headers?: Headers): Promise<Response> {
    if(typeof headers !== 'undefined') {
      return fetch(url, {
        method: 'DELETE',
        headers
      });
    }

    return fetch(url, {
      method: 'DELETE',
    });
  }
}
