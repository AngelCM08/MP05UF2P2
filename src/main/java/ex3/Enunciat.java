package ex3;

public class Enunciat {
/*
    1. Copia la classe HastTable de l'exercici anterior així com els seus jocs de proves dins aquest package i modifica
       el codi font perquè el faci servir (canvia "ex2" per "ex3" allà on toqui).

    2. Aplica-hi el mètode de refacció "extracció de classe" i explica per què creus que ha sigut convenient
       aplicar-los.
        He extret la funció psvm(String args[]) ja que hauria d'anar a una classe a part d'on es trobi la implementació
        de la HashTable en aquest cas. Si volguéssim utilitzar HashTable com a llibreria no tindria sentit que existís
        una funció main dins d'aquesta classe, ja que se suposa que no serà utilitzada mai.

    3. Aplica-hi el mètode de refacció "extracció de mètode" i explica per què creus que ha sigut convenient
       aplicar-los.
        En el meu cas, no tenia cap extracció de mètode possible amb el codi que tenía de l'exercici 2,
        l'he modificat per poder aplicar-hi aquesta tècnica, en un cas real no estic segur de si s'hauria de modificar
        el codi per poder-la aplicar, ja que es perdería molt temps en trobar els patrons de métodes que es poden
        extreure, encara que el codi sería més robust, no ha sigut massa complicat en aquest cas, la meva funció put()
        crec que ha millorat molt gràcies a l'extracció de métode, ja que modificant-la per poder aplicar la técnica l'he
        simplificat.


    4. Torna a executar els jocs de proves per a comprovar que el codi segueix funcionant correctament, ja que al
       fer-hi canvis refactoritzant es corre el risc de trencar el codi.
*/
}
