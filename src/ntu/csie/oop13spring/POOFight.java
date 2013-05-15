/* warning: this should not be modified but it is */
package ntu.csie.oop13spring;

public class POOFight{
    public static void main(String argv[]){
        POOArena arena = null;
        POOPet pet = null;
        if (argv.length < 3){
            System.out.println("Usage: java -jar hw4.jar arena_class pet1_class pet2_class ...");
            return;
        }
        
        String arena_class = argv[0];
        try{
            arena = (POOArena)(Class.forName(arena_class).newInstance());
        }
        catch(Exception e){
			System.out.print("fight1: ");
            System.out.println(e);
            return;
        }
        
        for(int i=1;i<argv.length;i++){
            String pet_class = argv[i];
            
            try{
                pet = (POOPet)(Class.forName(pet_class).newInstance());
                arena.addPet(pet);
            }
            catch(Exception e){
				System.out.print("fight2: ");
                System.out.println(e);
                return;
            }
        }
        while(arena.fight()){
            arena.show();
        }
    }
}
