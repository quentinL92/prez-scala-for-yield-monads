object Test extends App {

  /** For-yield **/

  // List
  val personnes: List[String] = List("Sarah", "Justin", "Eric")

  val emotions: List[String] = List("content(e)", "fatigue(e)", "passione(e)")

  // Sans yield = side-effect => action effectuée "sur le côtée" (ex: print) sans retourner de résultat (Unit)
  println("SANS yield\n")
  for {
    emotion <- emotions
    personne <- personnes
  } println(s"$personne est $emotion")

  println("---")

  for (emotion <- emotions; personne <- personnes) {
    println(s"$personne est $emotion")
  }

  println("---")

  emotions.foreach { emotion =>
    personnes.foreach { personne =>
      println(s"$personne est $emotion")
    }
  }

  // TODO over the grey text and print it out to show
  val res: Unit = for {
    emotion <- emotions
    personne <- personnes
  } s"$personne est $emotion"

  println("___________________________________________")

  // Avec yield => la fonction doit retourner quelques chose
  println("AVEC yield\n")

  val resYield: List[String] = for {
    emotion <- emotions
    personne <- personnes
  } yield s"$personne est $emotion"
  println(resYield.mkString("\n"))

  println("---")

  val resYieldMapped = emotions.flatMap { emotion =>
    personnes.map { personne =>
      s"$personne est $emotion"
    }
  }
  println(resYieldMapped.mkString("\n"))

  println("---")

  val frequences: List[String] = List("souvent", "parfois", "jamais")
  val personneFrequenceEmotion = for {
    personne <- personnes
    frequence <- frequences
    emotion <- emotions
  } yield s"$personne est $frequence $emotion"
  println(personneFrequenceEmotion.mkString("\n"))

  println("---")

  val personneFrequenceEmotionMapped = personnes.flatMap { personne =>
    frequences.flatMap { frequence =>
      emotions.flatMap { emotion =>
        s"$personne est $frequence $emotion"
      }
    }
  }
  println(personneFrequenceEmotionMapped.mkString("\n"))

  println("---")

  // Traduction de for yield en succession de flatMap et map
  /*
    for {
     a <- A
     b <- B
     } yield something

     toutes les flèches sont des flatMaps SAUF la dernière qui est un map donc la fonction est le yield
     ==> A.flatMap(a => B.map(b => something))

     for {
      a <- A
      b <- B
      c <- C
     } yield something

     ==> A.flatMap(a => B.flatMap(b => C.map(c => something)))
   */

  /** Notion de monade **/

  /*
    Est-ce qu'il n'y a rien qui vous a choqué jusqu'ici?
     La première fois que j'ai vu ça rien ne m'a choqué non plus...
     Parce que les for yield, les maps et flatMaps c'est simple quand on les explique avec des listes...
     SAUF QUE dans la réalité on n'utilise pas que des List.
     On utilise plusieurs autres types dont des Option, des Future ou encore des Either.

     Est-ce que le for yield se comporterait différemment pour d'autres types???
     EH BIEN NON c'est exactement la même chose et c'est ça qui est formidable!!
   */

  trait Monade[_] {
    def map[A, B](function: A => B): Monade[B]
    def flatMap[A, B](function: A => Monade[B]): Monade[B]
  }


}
