import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pet } from "../interface/pet.ts";

@Injectable({ providedIn: 'root' })
export class PetService {
  private apiUrl = "http://localhost:8080/search/list";

  constructor(private httpClient : HttpClient) {}

  getPets(searchValue?: string): Observable<Pet[]> {
    const param = searchValue ? { param : { searchValue } } : {};

    return this.httpClient.get<Pet[]>(this.apiUrl, param);
    }
  }
