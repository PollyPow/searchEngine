package searchEngine.searchEngine.modelOpensearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "zoo_animals")
public class ZooAnimalsIndex {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Integer, name = "age")
    private int ageYears;

    @Field(type = FieldType.Text, name = "natural_habit")
    private String naturalHabit;

    @Field(type = FieldType.Text, name = "species")
    private String species;

    @Field(type = FieldType.Text, name = "parents_names")
    private List<String> parents_names;

    @Field(type = FieldType.Text, name = "illnesses")
    private List<String> illnesses;

    @Field(type = FieldType.Integer, name = "years_in_zoo")
    private int yearsInZoo;

    @Field(type = FieldType.Text, name = "type_of_food")
    private String typeOfFood;

    @Field(type = FieldType.Integer, name = "times_to_feed_per_day")
    private int timesToFeedPerDay;

    public ZooAnimalsIndex() {
    }

    public ZooAnimalsIndex(String id, String name, int ageYears, String naturalHabit, String species, List<String> parents_names, List<String> illnesses, int yearsInZoo, String typeOfFood, int timesToFeedPerDay) {
        this.id = id;
        this.name = name;
        this.ageYears = ageYears;
        this.naturalHabit = naturalHabit;
        this.species = species;
        this.parents_names = parents_names;
        this.illnesses = illnesses;
        this.yearsInZoo = yearsInZoo;
        this.typeOfFood = typeOfFood;
        this.timesToFeedPerDay = timesToFeedPerDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAgeYears() {
        return ageYears;
    }

    public void setAgeYears(int ageYears) {
        this.ageYears = ageYears;
    }

    public String getNaturalHabit() {
        return naturalHabit;
    }

    public void setNaturalHabit(String naturalHabit) {
        this.naturalHabit = naturalHabit;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public List<String> getParents_names() {
        return parents_names;
    }

    public void setParents_names(List<String> parents_names) {
        this.parents_names = parents_names;
    }

    public List<String> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(List<String> illnesses) {
        this.illnesses = illnesses;
    }

    public int getYearsInZoo() {
        return yearsInZoo;
    }

    public void setYearsInZoo(int yearsInZoo) {
        this.yearsInZoo = yearsInZoo;
    }

    public String getTypeOfFood() {
        return typeOfFood;
    }

    public void setTypeOfFood(String typeOfFood) {
        this.typeOfFood = typeOfFood;
    }

    public int getTimesToFeedPerDay() {
        return timesToFeedPerDay;
    }

    public void setTimesToFeedPerDay(int timesToFeedPerDay) {
        this.timesToFeedPerDay = timesToFeedPerDay;
    }
}
