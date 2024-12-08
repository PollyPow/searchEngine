import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pet } from "../interface/pet";

@Injectable({ providedIn: 'root' })
export class PetService {
  private apiUrl = "http://localhost:8080/search/list";

  constructor(private httpClient : HttpClient) {}

  getPets(searchValue?: string): Observable<Pet[]> {
    const encodedSearchValue = encodeURIComponent(searchValue || 'default');
    return this.httpClient.get<Pet[]>(`${this.apiUrl}?input=${encodedSearchValue}`);
    }
  }
