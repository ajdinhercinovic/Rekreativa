package com.example.rekreativa

object RekreativaData {

    val sports = listOf(
        Sport("fudbal", "Fudbal", R.drawable.fudbal_ic),
        Sport("kosarka", "Košarka", R.drawable.kosarka_ic),
        Sport("odbojka", "Odbojka", R.drawable.odbojka_ic),
        Sport("tenis", "Tenis", R.drawable.tenis_ic),
        Sport("kuglanje", "Kuglanje", R.drawable.kuglanje_ic),
        Sport("bilijar", "Bilijar", R.drawable.bilijar_ic),
        Sport("borba", "Borba", R.drawable.borba_ic),
        Sport("karting", "Karting", R.drawable.karting_ic)
    )

    val tereni = listOf(
        Teren(
            id = "tusanj",
            naziv = "POMOĆNI STADION TUŠANJ",
            opis = "Pomoćni teren stadiona Tušanj, idealan za mali nogomet i treninge. Uređena podloga, dostupno u terminima popodne i navečer.",
            sportIds = listOf("fudbal"),
            cijenaPoSatu = "50 BAM/H",
            preporucenoOsoba = "8 - 14",
            slikaRes = R.drawable.teren_tusanj
        ),
        Teren(
            id = "mejdan",
            naziv = "SKPC MEJDAN",
            opis = "Sportsko-kulturno-privredni centar Mejdan jedan je od najpoznatijih sportskih objekata u Tuzli. Posjeduje više dvorana prilagođenih različitim sportovima – košarci, malom nogometu i odbojci.",
            sportIds = listOf("fudbal", "kosarka", "odbojka"),
            cijenaPoSatu = "70 BAM/H",
            preporucenoOsoba = "10 - 30",
            slikaRes = R.drawable.teren_mejdan
        ),
        Teren(
            id = "slana_banja_tenis",
            naziv = "TENIS SLANA BANJA",
            opis = "Kompleks teniskih terena je dio šireg sportskog i rekreacijskog područja Slana Banja, koje nudi i staze za šetnju i jogging, igrališta za druge sportove te spomen-obilježja.",
            sportIds = listOf("tenis"),
            cijenaPoSatu = "40 BAM/H",
            preporucenoOsoba = "2-4",
            slikaRes = R.drawable.slana_banja_tenis
        )
    )

    val currentUser = User(
        username = "herc1n",
        ime = "Ajdin",
        prezime = "Herčinović",
        email = "ajdinhercinovic03@gmail.com",
        telefon = "+387 61 433-220",
        datumRodjenja = "22.04.2003",
        avatarRes = R.drawable.ajdin_avatar
    )
    val users = listOf(
        User(
            username = "herc1n",
            ime = "Ajdin",
            prezime = "Hercinovic",
            avatarRes = R.drawable.ajdin_avatar,
            email = "ajdinhercinovic03@gmail.com",
            telefon = "+387 61 433-220",
            datumRodjenja = "22.04.2003"
        ),
        User(
            username = "dzumhur",
            ime = "Damir",
            prezime = "Dzumhur",
            avatarRes = R.drawable.damir_avatar,
            email = "damirdzzumhur@gmail.com",
            telefon = "+387 61 234-245",
            datumRodjenja = "19.03.1995"
        ),
        User(
            username = "keno",
            ime = "Kenan",
            prezime = "Softić",
            avatarRes = R.drawable.avatar_kenan,
            email = "kenosoft@gmail.com",
            telefon = "+387 61 623 311",
            datumRodjenja = "12.06.2004"
        ),
        User(
            username = "amar",
            ime = "Amar",
            prezime = "Hasić",
            avatarRes = R.drawable.avatar_kenan,
            email = "ajdinhercinovic03@gmail.com",
            telefon = "+387 61 123-220",
            datumRodjenja = "15.08.2003"
        )
    )
    fun userByUsername(id: String): User? = users.firstOrNull { it.username == id }

    fun terenById(id: String): Teren? = tereni.firstOrNull { it.id == id }

    val dogadjaji = listOf(
        Dogadjaj(
            id = "d1",
            tip = DogadjajTip.REKREACIJA,
            sportId = "fudbal",
            lokacijaTerenId = "tusanj",
            datumLabel = "UTORAK 11.12.2025.",
            vrijemeLabel = "18:00h - 19:00h",
            maxUcesnika = 14,
            prijavljeno = 7,
            pristup = PristupTip.ZAHTJEV,
            organizatorIme = "Ajdin Herčinović",
            organizatorAvatarRes = R.drawable.ajdin_avatar
        ),
        Dogadjaj(
            id = "d2",
            tip = DogadjajTip.TRENING,
            sportId = "tenis",
            lokacijaTerenId = "slana_banja_tenis",
            datumLabel = "SRIJEDA 20.11.2025.",
            vrijemeLabel = "13:00h - 15:00h",
            maxUcesnika = 4,
            prijavljeno = 3,
            pristup = PristupTip.OTVOREN,
            organizatorIme = "Damir Džumhur",
            organizatorAvatarRes = R.drawable.damir_avatar
        ),
        Dogadjaj(
            id = "d3",
            tip = DogadjajTip.TURNIR,
            sportId = "kosarka",
            lokacijaTerenId = "mejdan",
            datumLabel = "PETAK 06.12.2025.",
            vrijemeLabel = "20:00h - 21:30h",
            maxUcesnika = 20,
            prijavljeno = 6,
            pristup = PristupTip.OTVOREN,
            organizatorIme = "Rekreativa tim",
            organizatorAvatarRes = R.drawable.rekreativa_avatar
        )
    )

    val termini = listOf(
        Termin(
            id = "t1",
            tip = "Rekreacija",
            terenId = "mejdan",
            sportId = "kosarka",
            datumLabel = "PETAK 06.12.2025.",
            vrijemeLabel = "20:00h - 21:30h",
            creator = "Ajdin",
            creator_avatar = R.drawable.ajdin_avatar,
            isCreator = true
        ),
        Termin(
            id = "t2",
            tip = "Trening",
            terenId = "tusanj",
            sportId = "fudbal",
            datumLabel = "UTORAK 11.12.2025.",
            vrijemeLabel = "18:00h - 19:00h",
            creator = "Kenan",
            creator_avatar = R.drawable.avatar_kenan,
            isCreator = false
        ),
        Termin(
            id = "t3",
            tip = "Turnir",
            terenId = "slana_banja_tenis",
            sportId = "tenis",
            datumLabel = "SRIJEDA 20.11.2025.",
            vrijemeLabel = "13:00h - 15:00h",
            creator = "Damir",
            creator_avatar = R.drawable.damir_avatar,
            isCreator = false
        )
    )

    val pozivi = listOf(
        Poziv(
            id = "p1",
            tip = PozivTip.PRIMLJEN,
            terenId = "mejdan",
            sportId = "odbojka",
            datumLabel = "SUBOTA 14.12.2025.",
            vrijemeLabel = "19:00h - 20:00h",
            korisnikIme = "Amar",
            korisnikAvatarRes = R.drawable.avatar_amar
        ),

        Poziv(
            id = "p2",
            tip = PozivTip.POSLAN,
            terenId = "tusanj",
            sportId = "fudbal",
            datumLabel = "NEDJELJA 15.12.2025.",
            vrijemeLabel = "17:30h - 18:30h",
            korisnikIme = "Kenan",
            korisnikAvatarRes = R.drawable.avatar_kenan
        )
    )

    fun sportById(id: String): Sport? = sports.firstOrNull { it.id == id }

    // “Moji sportovi” (odaberi iz sports liste koju već imaš)
    val mySports = listOf(
        sportById("fudbal"),
        sportById("kosarka"),
        sportById("tenis"),
        sportById("bilijar"),
        sportById("karting")
    )

    val myStats = listOf(
        SportStat(sportIconRes = sportById("fudbal")!!.iconRes, brojTermina = 17, fairPlay = 4.7, znanje = 3.2),
        SportStat(sportIconRes = sportById("kosarka")!!.iconRes, brojTermina = 8, fairPlay = 4.9, znanje = 3.7),
        SportStat(sportIconRes = sportById("tenis")!!.iconRes, brojTermina = 6, fairPlay = 4.2, znanje = 4.5),
        SportStat(sportIconRes = sportById("bilijar")!!.iconRes, brojTermina = 4, fairPlay = 4.0, znanje = 3.0),
        SportStat(sportIconRes = sportById("karting")!!.iconRes, brojTermina = 3, fairPlay = 4.3, znanje = 4.0)
    )


}
