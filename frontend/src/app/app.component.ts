import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { Pet } from "./interface/pet";
import { PetService } from "./service/pet.service";

@Component({
  selector: 'app-root',
    standalone: true,
    imports: [CommonModule, HttpClientModule, ReactiveFormsModule],
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  pets: Pet[] = [];
  searchForm: FormGroup;

  constructor(private petService : PetService, private fb : FormBuilder) {
      this.searchForm = this.fb.group({ searchValue: '' });
    }

  ngOnInit(): void {
    this.fetchData();
    }

  onSearch(): void {
      this.fetchData();
    }

  fetchData(): void {
    const searchValue = this.searchForm.get('searchValue')?.value || '';
    this.petService.getPets(searchValue).subscribe((pets) => { this.pets = pets; });
    }
}
