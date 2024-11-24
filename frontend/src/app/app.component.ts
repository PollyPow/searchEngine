import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { Pet } from "./interface/pet.ts";
import { PetService } from "./service/pet.service.ts";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HttpClientModule],
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  searchValue = '';
  pets: Pet[] = [];

  constructor(private petService : PetService) {}

  ngOnInit(): void {
    fetchData();
    }

  fetchData(): void {
    this.petService.getPets(searchValue).subscribe((pets) => { this.pet = pets; });
    }
}
