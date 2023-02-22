package ex2;

import org.junit.jupiter.api.Assertions;

class HashTableTest {
    @org.junit.jupiter.api.Test
    void put() {
        HashTable hashTable = new HashTable();
        // Inserir un element que no col·lisiona dins una taula vuida (sense elements).
        hashTable.put("Clau1", "Element1");
        Assertions.assertEquals("\n bucket[4] = [Clau1, Element1]", hashTable.toString());

        // Inserir un element que no col·lisiona dins una taula no vuida (amb elements).
        hashTable.put("Clau2", "Element2");
        Assertions.assertEquals("\n bucket[4] = [Clau1, Element1]\n bucket[5] = [Clau2, Element2]",hashTable.toString());

        // Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 2a posició dins el mateix bucket.
        hashTable.put("5", "Element3"); // Utilitzant la funció: hashTable.getCollisionsForKey("Clau2") he obtingut que '5' colisiona amb 'Clau2'
        Assertions.assertEquals( "\n" +
                " bucket[4] = [Clau1, Element1]\n" +
                " bucket[5] = [Clau2, Element2] -> [5, Element3]", hashTable.toString());

        // Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 3a posició dins el mateix bucket.
        hashTable.put("05", "Element4"); // Utilitzant la funció: hashTable.getCollisionsForKey("5") he obtingut que '05' colisiona amb '5'
        Assertions.assertEquals( "\n" +
                " bucket[4] = [Clau1, Element1]\n" +
                " bucket[5] = [Clau2, Element2] -> [5, Element3] -> [05, Element4]", hashTable.toString());

        // Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula no vuida.
        hashTable.put("Clau2", "Element5");
        Assertions.assertEquals( "\n" +
                " bucket[4] = [Clau1, Element1]\n" +
                " bucket[5] = [Clau2, Element5] -> [5, Element3] -> [05, Element4]", hashTable.toString());
        /* ERROR AL CODI: Un cop executat aquest Assetion m'he adonat que hi ha un error al codi, ja que hashTable s'ha creat de la següent manera en comptes de la esperada:
            bucket[4] = [Clau1, Element1]
            bucket[5] = [Clau2, Element2] -> [5, Element3] -> [05, Element4] -> [Clau2, Element5]
           Com es pot veure, en comptes d'actualitzar, s'ha creat un nou element amb la clau repetida "[Clau2, Element5]"
           L'error es troba a ......................
        */

        // Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (1a posició) dins una taula no vuida.


        // Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (2a posició) dins una taula no vuida.


        // Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (3a posició) dins una taula no vuida.


         }

    @org.junit.jupiter.api.Test
    void get() {

    }

    @org.junit.jupiter.api.Test
    void drop() {

    }

    @org.junit.jupiter.api.Test
    void count() {

    }

    @org.junit.jupiter.api.Test
    void size() {

    }
}