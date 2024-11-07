package searchEngine.searchEngine.modelOpensearch;

import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.UUID;

@Document(indexName = "my_pets")
public class MyPetsIndex{
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Integer, name = "age")
    private int ageYears;

    @Field(type = FieldType.Keyword, name = "pet_type")
    private PetType petType;

    @Field(type = FieldType.Text, name = "breed")
    private String breed;

    @Field(type = FieldType.Text, name = "parents_names")
    private List<String> parentsNames;

    @Field(type = FieldType.Text, name = "illnesses")
    private List<String> illnesses;

    @Field(type = FieldType.Text, name = "previous_owners_name")
    private String previousOwnersName;

    @Field(type = FieldType.Text, name = "food_brand")
    private String foodBrand;

    public MyPetsIndex() {
        this.id = UUID.randomUUID().toString();
    }

    public MyPetsIndex(String name, int ageYears, PetType petType, String breed, List<String> parentsNames, List<String> illnesses, String previousOwnersName, String foodBrand) {
        this.name = name;
        this.ageYears = ageYears;
        this.petType = petType;
        this.breed = breed;
        this.parentsNames = parentsNames;
        this.illnesses = illnesses;
        this.previousOwnersName = previousOwnersName;
        this.foodBrand = foodBrand;
    }

    public String getId() { return id; }

    public String getName() {
        return name;
    }

    public int getAgeYears() {
        return ageYears;
    }

    public PetType getPetType() {
        return petType;
    }

    public String getBreed() {
        return breed;
    }

    public List<String> getParentsNames() {
        return parentsNames;
    }

    public List<String> getIllnesses() {
        return illnesses;
    }

    public String getPreviousOwnersName() {
        return previousOwnersName;
    }

    public String getFoodBrand() {
        return foodBrand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAgeYears(int ageYears) {
        this.ageYears = ageYears;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setParentsNames(List<String> parentsNames) {
        this.parentsNames = parentsNames;
    }

    public void setIllnesses(List<String> illnesses) {
        this.illnesses = illnesses;
    }

    public void setPreviousOwnersName(String previousOwnersName) {
        this.previousOwnersName = previousOwnersName;
    }

    public void setFoodBrand(String foodBrand) {
        this.foodBrand = foodBrand;
    }
}
