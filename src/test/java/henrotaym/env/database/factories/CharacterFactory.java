package henrotaym.env.database.factories;

@AllArgsConstructor
@Component
public class CharacterFactory extends EntityFactory<Character> {
      public CharacterFactory(Faker faker, CharacterRepository charachterRepository){
            super(faker,charachterRepository);
      }

      @Override
      public Character entity(){
            retrun new Character();
      }

      @Override
      public void attributes(Character character){
            character.setApiCharacterId();
            character.setName();
            character.setStatus();
            character.setImage();
      }

      @Override
      public void relationships(Character character){
            
      }


}
