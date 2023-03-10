package ex3;

// Original source code: https://gist.github.com/amadamala/3cdd53cb5a6b1c1df540981ab0245479
// Modified by Fernando Porrino Serrano for academic purposes.
// Modified by Ángel Castro Merino for academic purposes.

import java.util.ArrayList;

/**
 * Implementació d'una taula de hash sense col·lisions.
 * Original source code: https://gist.github.com/amadamala/3cdd53cb5a6b1c1df540981ab0245479
 */
public class HashTable {
    private int SIZE = 16;
    private int ITEMS = 0;
    private HashEntry[] entries = new HashEntry[SIZE];

    public int count(){
        return this.ITEMS;
    }

    public int size(){
        return this.SIZE;
    }

    /**
     * Permet afegir un nou element a la taula.
     * @param key La clau de l'element a afegir.
     * @param value El propi element que es vol afegir.
     */
    public void put(String key, String value) {
        int hash = getHash(key);
        final HashEntry hashEntry = new HashEntry(key, value);

        if(entries[hash] == null) { // Si no hi ha colisions s'emmagatzema sense problemes.
            entries[hash] = hashEntry;

            ITEMS++;
        }else{
            // S'iteren els elements que puguin haver llistats a partir de temp per
            // cercar coincidències de claus, si es troba es canvia el seu valor.
            HashEntry temp = getHashEntry(key, hash);

            // En cas de no haver trobat cap clau que coincideixi i ens trobem a
            // l'últim valor, comprovem si hi ha una coincidència de claus en
            // aquest o afegim la nova entrada com a next d'aquesta última.
            if(temp.key.equals(key)){
                temp.value = hashEntry.value;
            }else{
                temp.next = hashEntry;
                hashEntry.prev = temp;

                ITEMS++;
            }
        }
    }

    /*
    public void put(String key, String value) {
        int hash = getHash(key);
        final HashEntry hashEntry = new HashEntry(key, value);

        if(entries[hash] == null) { // Si no hi ha colisions s'emmagatzema sense problemes.
            entries[hash] = hashEntry;

            ITEMS++;
        }else {
            HashEntry temp = entries[hash];

            // Si el primer valor coincideix a la key amb la introduïda es substitueix el value
            if(temp.key.equals(key)){
                entries[hash].value = hashEntry.value;
                return;
            }

            // S'iteren els elements que puguin haver llistats a partir de temp per
            // cercar coincidències de claus, si es troba es canvia el seu valor.
            while(temp.next != null){
                if(temp.key.equals(key)){
                    temp.value = hashEntry.value;
                    return;
                }else{
                    temp = temp.next;
                }
            }

            // En cas de no haver trobat cap clau que coincideixi i ens trobem a
            // l'últim valor, comprovem si hi ha una coincidència de claus en
            // aquest o afegim la nova entrada com a next d'aquesta última.
            if(temp.key.equals(key)){
                temp.value = hashEntry.value;
            }else{
                temp.next = hashEntry;
                hashEntry.prev = temp;

                ITEMS++;
            }
        }
    }
    */


    /**
     * Permet recuperar un element dins la taula.
     * @param key La clau de l'element a trobar.
     * @return El propi element que es busca (null si no s'ha trobat).
     */
    public String get(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {
            // He afegit com a condició per avançar al següent element que existeixi
            // per solucionar l'error, en el cas que es busqui un element que no
            // existeix, es retornarà null tal i com indica la descripció de la funció.
            HashEntry temp = getHashEntry(key, hash);
            if (!temp.key.equals(key)) return null;
            return temp.value;
        }
        return null;
    }

    /*
    public String get(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {
            HashEntry temp = entries[hash];

            while(!temp.key.equals(key)){
                if(temp.next != null) temp = temp.next;
                else return null;
            }
            return temp.value;
        }
        return null;
    }
    */


    /**
     * Permet esborrar un element dins de la taula.
     * @param key La clau de l'element a trobar.
     */
    public void drop(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {
            // Si l'element que es busca no existeix surt de la funció.
            HashEntry temp = getHashEntry(key, hash);
            if (!temp.key.equals(key)) return;

            /*
            HashTable.HashEntry temp = entries[hash];
            while(!temp.key.equals(key)){ // Si l'element que es busca no existeix surt de la funció.
                if(temp.next != null) temp = temp.next;
                else return;
            }
            */

            if(temp.prev == null){ // Per saber si l'element a esborrar és el primer
                if(temp.next == null) entries[hash] = null; //esborrar element únic (no col·lissió)
                else { // Si l'element és el primer però colisiona.
                    entries[hash] = temp.next;
                    entries[hash].prev = null;
                }
            }else if(temp.next != null) { // Per saber si l'element a esborrar ésta pel mig
                temp.next.prev = temp.prev;
                temp.prev.next = temp.next;
            }else {
                temp.prev.next = null; // Sabem que l'element a esborrar és l'últim
                temp.prev = null; // Encara que no és necessari, s'indica per evitar un possible cas d'error.
            }
            ITEMS--;
        }
    }


    /**
     * Funció creada amb l'extracció de métodes, situa temp
     * en la posició on coincideix amb la key o retorna null.
     *
     * @param key La clau de l'element a trobar.
     * @return El propi element que es busca (null si no s'ha trobat).
     */
    private HashEntry getHashEntry(String key, int hash) {
        HashEntry temp = entries[hash];
        while(!temp.key.equals(key) && temp.next != null){
            temp = temp.next;
        }
        return temp;
    }


    private int getHash(String key) {
        // piggy backing on java string
        // hashcode implementation.
        return key.hashCode() % SIZE;
    }

    private class HashEntry {
        String key;
        String value;

        // Linked list of same hash entries.
        HashEntry next;
        HashEntry prev;

        public HashEntry(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }

    @Override
    public String toString() {
        int bucket = 0;
        StringBuilder hashTableStr = new StringBuilder();
        for (HashEntry entry : entries) {
            if(entry == null) {
                bucket++;
                continue;
            }

            hashTableStr.append("\n bucket[")
                    .append(bucket)
                    .append("] = ")
                    .append(entry.toString());
            bucket++;
            HashEntry temp = entry.next;
            while(temp != null) {
                hashTableStr.append(" -> ");
                hashTableStr.append(temp.toString());
                temp = temp.next;
            }
        }
        return hashTableStr.toString();
    }

    /**
     * Permet calcular quants elements col·lisionen (produeixen la mateixa posició dins la taula de hash) per a la clau donada.
     * @param key La clau que es farà servir per calcular col·lisions.
     * @return Una clau que, de fer-se servir, provoca col·lisió amb la que s'ha donat.
     */
    public String getCollisionsForKey(String key) {
        return getCollisionsForKey(key, 1).get(0);
    }

    /**
     * Permet calcular quants elements col·lisionen (produeixen la mateixa posició dins la taula de hash) per a la clau donada.
     * @param key La clau que es farà servir per calcular col·lisions.
     * @param quantity La quantitat de col·lisions a calcular.
     * @return Un llistat de claus que, de fer-se servir, provoquen col·lisió.
     */
    public ArrayList<String> getCollisionsForKey(String key, int quantity){
        /*
          Main idea:
          alphabet = {0, 1, 2}

          Step 1: "000"
          Step 2: "001"
          Step 3: "002"
          Step 4: "010"
          Step 5: "011"
           ...
          Step N: "222"

          All those keys will be hashed and checking if collides with the given one.
        * */

        final char[] alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        ArrayList<Integer> newKey = new ArrayList<>();
        ArrayList<String> foundKeys = new ArrayList<>();

        newKey.add(0);
        int collision = getHash(key);
        int current = newKey.size() -1;

        while (foundKeys.size() < quantity){
            //building current key
            String currentKey = "";
            for(int i = 0; i < newKey.size(); i++)
                currentKey += alphabet[newKey.get(i)];

            if(!currentKey.equals(key) && getHash(currentKey) == collision)
                foundKeys.add(currentKey);

            //increasing the current alphabet key
            newKey.set(current, newKey.get(current)+1);

            //overflow over the alphabet on current!
            if(newKey.get(current) == alphabet.length){
                int previous = current;
                do{
                    //increasing the previous to current alphabet key
                    previous--;
                    if(previous >= 0)  newKey.set(previous, newKey.get(previous) + 1);
                }
                while (previous >= 0 && newKey.get(previous) == alphabet.length);

                //cleaning
                for(int i = previous + 1; i < newKey.size(); i++)
                    newKey.set(i, 0);

                //increasing size on underflow over the key size
                if(previous < 0) newKey.add(0);

                current = newKey.size() -1;
            }
        }

        return  foundKeys;
    }

    /*
    public static void main(String[] args) {
        HashTable hashTable = new HashTable();

        // Put some key values.
        for(int i=0; i<30; i++) {
            final String key = String.valueOf(i);
            hashTable.put(key, key);
        }

        // Print the HashTable structure
        log("****   HashTable  ***");
        log(hashTable.toString());
        log("\nValue for key(20) : " + hashTable.get("20") );
    }
    */
    protected static void log(String msg) {
        System.out.println(msg);
    }
}