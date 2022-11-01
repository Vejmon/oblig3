package no.oslomet.cs.algdat.Oblig3;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "nullverdi forsøkt i leggInn");
        //inspirert av programkode 5.2.3.a i

        if(antall() ==0){
            rot = new Node<T>(verdi,null,null,null);
            antall++;
            return true;
        }
        Node<T> aktivNode = rot;
        Node<T> forrige = rot;
        int verdi_større_aktiv = 0;

        while (aktivNode != null){
            verdi_større_aktiv = comp.compare(verdi, aktivNode.verdi);
            if (verdi_større_aktiv < 0){
                forrige = aktivNode;
                aktivNode = aktivNode.venstre;
            }
            else {
                forrige = aktivNode;
                aktivNode = aktivNode.høyre;
            }
        }
        aktivNode = new Node<>(verdi,null,null,forrige);

        if (verdi_større_aktiv < 0) forrige.venstre = aktivNode;
        else forrige.høyre = aktivNode;

        antall++;
        return true;
    }

    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        // null er ikke i listen.
        if (verdi == null){
            return 0;
        }

        Node<T> aktiv = rot, forrige = rot;
        int like = 0, mindreEnnAktiv = 0;

        while (aktiv != null) {
            mindreEnnAktiv = comp.compare(verdi, aktiv.verdi);
            if (mindreEnnAktiv >= 0){
                if (mindreEnnAktiv ==0) like++;
                aktiv = aktiv.høyre;
            }
            else {
                aktiv = aktiv.venstre;
            }
        }
        return like;
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        Node<T> ut = p;
        while (p != null){
            ut = p;
            p = p.venstre;
        }
        return ut;
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postorden(Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


    public static void main(String[] args){
        Integer[] a = {4,7,5,2,9,4,5,10,5,8,7,4,6,5,6,5};
        SBinTre<Integer> tre = new SBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) { tre.leggInn(verdi); }
        System.out.println(tre.antall()); // Utskrift: 10
        System.out.println(tre.antall(5)); // Utskrift: 0
        System.out.println(tre.antall(4)); // Utskrift: 3
        System.out.println(tre.antall(7)); // Utskrift: 2
        System.out.println(tre.antall(10)); // Utskrift: 1
    }

} // ObligSBinTre
