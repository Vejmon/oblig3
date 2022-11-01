# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Vemund Hellekleiv, s362068, s362068@oslomet.no


# Oppgavebeskrivelse

I oppgave 1 så gikk jeg ved å se på programkoden i 5.2.3.a), testet en del med duplikater, og hadde problemer med at de la seg litt hulter i bulter.
dette oppdaget jeg først når jeg prøvde å finne antall duplikater i opg2
fikk tilslutt orden på det, etter å ha lest meg opp på definisjonen av reglene for å sette inn i treet. 
(at man legger inn til venstre hvis den nye er mindre, ellers høyre)

I oppgave 2 så brukte jeg en tegneblogg til å visualisere problemet mitt, og så at duplikatene mine havnet mange rare plasser i treet mitt,
jeg måtte gå tilbake til legginn metoden og få ordnings på den først. Så la jeg inn spesialltilfelle for null verdi, og fikset loopen min, da den hoppet ut av løkka
før den hadde sjekket den siste den var på og inkrementert antallet hvis verdien var lik nodens verdi.

I opg3 så måtte jeg finne frem pen og papir igjen og tegne treet som jeg ble laget, leggInn funksjonen gjør som den skal,
men førstePostorden og nestePostorden roter det til enn så lenge. Var bare en bagatell, rekursjerte med p sitt høyrebarn og ikke p sin høyre nabo...