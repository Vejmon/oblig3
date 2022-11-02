package no.oslomet.cs.algdat.Oblig3;


import java.awt.event.ItemListener;
import java.util.*;

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
            rot = new Node<>(verdi,null,null,null);
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
        else {
            assert forrige != null;
            forrige.høyre = aktivNode;
        }

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

        //sjekker fort om det finnes en i det hele tatt
        if (!inneholder(verdi)) return 0;

        Node<T> aktiv = rot, forrige = rot;
        int like = 0;

        while (aktiv != null) {
            int mindreEnnAktiv = comp.compare(verdi, aktiv.verdi);
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
            if (p.venstre != null) p = p.venstre;
            else p = p.høyre;
        }
        return ut;
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {

        /*tatt fra læreboka
         Postorden:
            Hvis p ikke har en forelder (p er rotnoden), så er p den siste i postorden.
            Hvis p er høyre barn til sin forelder f, er forelderen f den neste.
            Hvis p er venstre barn til sin forelder f, gjelder:
                Hvis p er enebarn (f.høyre er null), er forelderen f den neste.
                Hvis p ikke er enebarn (dvs. f.høyre er ikke null), så er den neste den noden som kommer først i postorden i subtreet med f.høyre som rot.
        */
        //hvis p er roten
        if (p.forelder == null) return null;

        //hvis p er høyre barn
        else if (p.equals(p.forelder.høyre)) return p.forelder;

        //hvis p er venstre barn
        else {
            //hvis p er enebarn
            if (p.forelder.høyre == null) return p.forelder;
            //hvis høyre Node finnes
            else return førstePostorden(p.forelder.høyre);
        }
    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> aktiv = førstePostorden(rot);
        while (aktiv!= null) {
            oppgave.utførOppgave(aktiv.verdi);
            aktiv = nestePostorden(aktiv);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p.equals(rot))  p = førstePostorden(p);
        else p = nestePostorden(p);

        Objects.requireNonNull(p, "forsøkt å rekursere med null node");
        if (p.equals(rot)){
            oppgave.utførOppgave(p.verdi);
            return;
        }
        oppgave.utførOppgave(p.verdi);
        postordenRecursive(p, oppgave);
    }

    public ArrayList<T> serialize() {

        if (rot == null) return new ArrayList<>();

        LinkedList<Node<T>> holder = new LinkedList<>();
        holder.add(rot);
        ArrayList<T> utList = new ArrayList<>();

        while (holder.size()> 0){
            Node<T> aktivNode = holder.remove();
            if (aktivNode.venstre != null) holder.add(aktivNode.venstre);
            if (aktivNode.høyre != null) holder.add(aktivNode.høyre);
            utList.add(aktivNode.verdi);
        }
        return utList;
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {

        SBinTre<K> mitt = new SBinTre<K>(c);
        for (K del:data) {
            mitt.leggInn(del);
        }
        return mitt;
    }

} // ObligSBinTre
