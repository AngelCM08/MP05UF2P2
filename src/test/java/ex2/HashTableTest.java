package ex2;

import org.junit.jupiter.api.Assertions;

class HashTableTest {

    // TESTS DE LA FUNCIÓ put()

    // Inserir un element que no col·lisiona dins una taula vuida (sense elements).
    @org.junit.jupiter.api.Test
    void putTaulaBuida() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau1", "Element1");
        Assertions.assertEquals("\n bucket[4] = [Clau1, Element1]", hashTable.toString());
    }

    // Inserir un element que no col·lisiona dins una taula no vuida (amb elements).
    @org.junit.jupiter.api.Test
    void putNoColisionaTaulaNoBuida() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau1", "Element1");
        hashTable.put("Clau2", "Element2");
        Assertions.assertEquals("\n bucket[4] = [Clau1, Element1]\n bucket[5] = [Clau2, Element2]",hashTable.toString());
    }

    // Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 2a posició dins el mateix bucket.
    @org.junit.jupiter.api.Test
    void putColisionaPrimerCop() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element2");
        hashTable.put("5", "Element3"); // Utilitzant la funció: hashTable.getCollisionsForKey("Clau2") he obtingut que '5' colisiona amb 'Clau2'
        Assertions.assertEquals( "\n" +
                " bucket[5] = [Clau2, Element2] -> [5, Element3]", hashTable.toString());
    }

    // Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 3a posició dins el mateix bucket.
    @org.junit.jupiter.api.Test
    void putColisionaClauJaColisionada() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element2");
        hashTable.put("5", "Element3");
        hashTable.put("05", "Element4"); // Utilitzant la funció: hashTable.getCollisionsForKey("5") he obtingut que '05' colisiona amb '5'
        Assertions.assertEquals( "\n" +
                " bucket[5] = [Clau2, Element2] -> [5, Element3] -> [05, Element4]", hashTable.toString());
    }

    // Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula no vuida.
    @org.junit.jupiter.api.Test
    void putUpdateSenseColisio() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau1", "Element1");
        hashTable.put("Clau1", "Element5");
        Assertions.assertEquals( "\n" +
                " bucket[4] = [Clau1, Element5]", hashTable.toString());
    }

    // Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (1a posició) dins una taula no vuida.
    @org.junit.jupiter.api.Test
    void putUpdateAmbColisio1aPosicio() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element2");
        hashTable.put("5", "Element3");
        hashTable.put("05", "Element4");
        hashTable.put("Clau2", "Element6");
        Assertions.assertEquals( "\n" +
                " bucket[5] = [Clau2, Element6] -> [5, Element3] -> [05, Element4]", hashTable.toString());
        /* ERROR AL CODI: Un cop executat aquest Assertion m'he adonat que hi ha un error al codi de la funció put(), ja que hashTable s'ha creat de la següent manera en comptes de la esperada:
            bucket[4] = [Clau1, Element1]
            bucket[5] = [Clau2, Element2] -> [5, Element3] -> [05, Element4] -> [Clau2, Element5]
           Com es pot veure, en comptes d'actualitzar, s'ha creat un nou element amb la clau repetida "[Clau2, Element5]"
        */
    }

    // Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (2a posició) dins una taula no vuida.
    @org.junit.jupiter.api.Test
    void putUpdateAmbColisioPosicioIntermitja() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element6");
        hashTable.put("5", "Element3");
        hashTable.put("05", "Element4");
        hashTable.put("5", "Element7");
        Assertions.assertEquals( "\n" +
                " bucket[5] = [Clau2, Element6] -> [5, Element7] -> [05, Element4]", hashTable.toString());
    }

    // Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (3a posició) dins una taula no vuida.
    @org.junit.jupiter.api.Test
    void putUpdateAmbColisioUltimaPosicio() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element6");
        hashTable.put("5", "Element7");
        hashTable.put("05", "Element4");
        hashTable.put("05", "Element8");
        Assertions.assertEquals( "\n" +
                " bucket[5] = [Clau2, Element6] -> [5, Element7] -> [05, Element8]", hashTable.toString());
    }


    // TESTS DE LA FUNCIÓ get()

    // Obtenir un element que no col·lisiona dins una taula buida.
    @org.junit.jupiter.api.Test
    void getTaulaBuida() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau1", "Element1");
        Assertions.assertEquals("Element1", hashTable.get("Clau1"));
    }

    // Obtenir un element que col·lisiona dins una taula (1a posició dins el mateix bucket).
    @org.junit.jupiter.api.Test
    void getColisio1aPosicio() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element2");
        hashTable.put("5", "Element3");
        hashTable.put("05", "Element4");
        Assertions.assertEquals("Element2", hashTable.get("Clau2"));
    }

    // Obtenir un element que col·lisiona dins una taula (2a posició dins el mateix bucket).
    @org.junit.jupiter.api.Test
    void getColisioPosicioIntermitja() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element2");
        hashTable.put("5", "Element3");
        hashTable.put("05", "Element4");
        Assertions.assertEquals("Element3", hashTable.get("5"));
    }

    // Obtenir un element que col·lisiona dins una taula (3a posició dins el mateix bucket).
    @org.junit.jupiter.api.Test
    void getColisioUltimaPosicio() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element2");
        hashTable.put("5", "Element3");
        hashTable.put("05", "Element4");
        Assertions.assertEquals("Element4", hashTable.get("05"));
    }

    // Obtenir un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
    @org.junit.jupiter.api.Test
    void getElementNoExisteixAmbPosicioBuida() {
        HashTable hashTable = new HashTable();
        Assertions.assertNull(hashTable.get("05"));
    }

    // Obtenir un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
    @org.junit.jupiter.api.Test
    void getElementNoExisteixAmbPosicioOcupadaNoColisiona() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element2");
        Assertions.assertNull(hashTable.get("05"));
        /* ERROR AL CODI: Un cop executat aquest Assertion m'he adonat que hi ha un error al codi de la funció get(),
            en aquest cas específic, la funció no retorna null, intenta retornar el value d'un element que no existeix
            i llença una exepció. */
    }

    // Obtenir un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
    @org.junit.jupiter.api.Test
    void getElementNoExisteixAmbPosicioOcupadaSiColisiona() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element2");
        hashTable.put("5", "Element3");
        hashTable.put("05", "Element4");
        Assertions.assertNull(hashTable.get("16")); // La clau 16 genera el HashCode 5, igual que els altres 3 elements.
    }


    // TESTS DE LA FUNCIÓ drop()

    // Esborrar un element que no col·lisiona dins una taula.
    @org.junit.jupiter.api.Test
    void dropTaula() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau1", "Element1");
        hashTable.drop("Clau1");
        Assertions.assertEquals("", hashTable.toString());
    }

    // Esborrar un element que col·lisiona dins una taula (1a posició dins el mateix bucket).
    @org.junit.jupiter.api.Test
    void dropColisio1aPosicio() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element6");
        hashTable.put("5", "Element3");
        hashTable.put("05", "Element4");
        hashTable.drop("Clau2");
        Assertions.assertEquals( "\n" +
                " bucket[5] = [5, Element3] -> [05, Element4]", hashTable.toString());
        /* ERROR AL CODI: Un cop executat aquest Assertion m'he adonat que hi ha un error
            al codi de la funció drop(), en aquest cas específic, la funció elimina tots
            els elements que colisionen.*/
    }

    // Esborrar un element que col·lisiona dins una taula (2a posició dins el mateix bucket).
    @org.junit.jupiter.api.Test
    void dropColisioPosicioIntermitja() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element6");
        hashTable.put("5", "Element3");
        hashTable.put("05", "Element4");
        hashTable.drop("5");
        Assertions.assertEquals( "\n" +
                " bucket[5] = [Clau2, Element6] -> [05, Element4]", hashTable.toString());
    }

    // Esborrar un element que col·lisiona dins una taula (3a posició dins el mateix bucket).
    @org.junit.jupiter.api.Test
    void dropColisioUltimaPosicio() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element6");
        hashTable.put("5", "Element3");
        hashTable.put("05", "Element4");
        hashTable.drop("05");
        Assertions.assertEquals( "\n" +
                " bucket[5] = [Clau2, Element6] -> [5, Element3]", hashTable.toString());
    }

    // Esborrar un elements que no existeix perquè la seva posició està buida (no hi ha cap element dins el bucket).
    /*@org.junit.jupiter.api.Test
    void dropElementNoExisteixAmbPosicioBuida() {
        HashTable hashTable = new HashTable();
        Assertions.assertNull(hashTable.get("05"));
    }

    // Esborrar un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
    @org.junit.jupiter.api.Test
    void dropElementNoExisteixAmbPosicioOcupadaNoColisiona() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element2");
        Assertions.assertNull(hashTable.get("05"));*/
        /* ERROR AL CODI: Un cop executat aquest Assertion m'he adonat que hi ha un error al codi de la funció get(),
            en aquest cas específic, la funció no retorna null, intenta retornar el value d'un element que no existeix
            i llença una exepció. */
    /*}

    // Esborrar un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
    @org.junit.jupiter.api.Test
    void dropElementNoExisteixAmbPosicioOcupadaSiColisiona() {
        HashTable hashTable = new HashTable();
        hashTable.put("Clau2", "Element2");
        hashTable.put("5", "Element3");
        hashTable.put("05", "Element4");
        Assertions.assertNull(hashTable.get("16")); // La clau 16 genera el HashCode 5, igual que els altres 3 elements.
    }*/



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