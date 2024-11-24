import { PetType } from "../enum/petType.enum.ts";

export interface Pet {
    id: string;
    name: string;
    ageYears: number;
    petType: PetType;
    breed: string;
    parentsNames: string[];
    illnesses: string[];
    previousOwnersName: string;
    foodBrand: string;
  }
