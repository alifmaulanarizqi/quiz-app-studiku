package com.example.kotlintemplate.data.datasource

import com.example.kotlintemplate.domain.model.Difficulty
import com.example.kotlintemplate.domain.model.Question
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionLocalDataSource @Inject constructor() {

    private val questions: List<Question> = listOf(
        // ==========================================
        // EASY: General trivia, basic geography/science
        // ==========================================
        Question(
            id = 1,
            text = "What is the capital city of France?",
            options = listOf("Paris", "London", "Berlin", "Rome"),
            correctAnswerIndex = 0,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 2,
            text = "Which planet is known as the Red Planet?",
            options = listOf("Venus", "Mars", "Jupiter", "Saturn"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 3,
            text = "How many continents are there on Earth?",
            options = listOf("5", "6", "7", "8"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 4,
            text = "What is the chemical symbol for water?",
            options = listOf("CO2", "NaCl", "O2", "H2O"),
            correctAnswerIndex = 3,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 5,
            text = "Which animal is commonly known as the King of the Jungle?",
            options = listOf("Tiger", "Lion", "Elephant", "Gorilla"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 6,
            text = "What is the largest ocean on Earth?",
            options = listOf("Atlantic Ocean", "Indian Ocean", "Pacific Ocean", "Arctic Ocean"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 7,
            text = "Which gas do plants primarily absorb during photosynthesis?",
            options = listOf("Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 8,
            text = "How many days are there in a standard leap year?",
            options = listOf("364", "365", "366", "367"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 9,
            text = "What is the primary color of an emerald gemstone?",
            options = listOf("Blue", "Red", "Yellow", "Green"),
            correctAnswerIndex = 3,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 10,
            text = "Which organ pumps blood throughout the human body?",
            options = listOf("Heart", "Lungs", "Brain", "Kidney"),
            correctAnswerIndex = 0,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 11,
            text = "What is the boiling point of pure water at sea level?",
            options = listOf("90°C", "100°C", "110°C", "120°C"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 12,
            text = "Which instrument is used to measure temperature?",
            options = listOf("Barometer", "Thermometer", "Speedometer", "Altimeter"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 13,
            text = "What is the tallest living animal on Earth?",
            options = listOf("African Elephant", "Giraffe", "Hippopotamus", "Blue Whale"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 14,
            text = "In which country are the Great Pyramids of Giza located?",
            options = listOf("Greece", "Mexico", "Egypt", "Peru"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 15,
            text = "What is the hardest natural mineral substance found on Earth?",
            options = listOf("Gold", "Iron", "Diamond", "Quartz"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.EASY
        ),
        Question(
            id = 16,
            text = "How many distinct colors are traditionally recognized in a rainbow?",
            options = listOf("6", "7", "8", "9"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.EASY
        ),

        // ==========================================
        // MEDIUM: Science, history, and general knowledge
        // ==========================================
        Question(
            id = 101,
            text = "In which year did World War II officially end?",
            options = listOf("1943", "1944", "1945", "1948"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 102,
            text = "Which cellular organelle is widely known as the powerhouse of the cell?",
            options = listOf("Nucleus", "Ribosome", "Mitochondria", "Golgi apparatus"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 103,
            text = "What is the most abundant gas in Earth's atmosphere?",
            options = listOf("Oxygen", "Nitrogen", "Carbon Dioxide", "Argon"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 104,
            text = "Which ancient civilization built the historic citadel of Machu Picchu?",
            options = listOf("Aztec", "Maya", "Inca", "Olmec"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 105,
            text = "What is the deepest oceanic trench on Earth?",
            options = listOf("Puerto Rico Trench", "Java Trench", "Mariana Trench", "Philippine Trench"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 106,
            text = "Who developed the general theory of relativity in physics?",
            options = listOf("Isaac Newton", "Albert Einstein", "Niels Bohr", "Galileo Galilei"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 107,
            text = "Which famous barrier was constructed in 1961 and fell in 1989 during the Cold War?",
            options = listOf("Great Wall of China", "Berlin Wall", "Hadrian's Wall", "Western Wall"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 108,
            text = "What is the chemical symbol for gold on the periodic table?",
            options = listOf("Ag", "Fe", "Au", "Pb"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 109,
            text = "Which river is widely recognized as the longest in the world?",
            options = listOf("Amazon River", "Nile River", "Yangtze River", "Mississippi River"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 110,
            text = "In which year did astronauts first land on the Moon during the Apollo 11 mission?",
            options = listOf("1965", "1969", "1971", "1975"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 111,
            text = "What classification of star is our Sun?",
            options = listOf("Red Giant", "Yellow Dwarf", "White Dwarf", "Blue Supergiant"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 112,
            text = "Who served as the first President of the United States?",
            options = listOf("Thomas Jefferson", "George Washington", "Abraham Lincoln", "Benjamin Franklin"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 113,
            text = "What is the approximate speed of sound in dry air at 20°C?",
            options = listOf("140 m/s", "240 m/s", "343 m/s", "440 m/s"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 114,
            text = "Which ancient empire was ruled by figures such as Julius Caesar and Augustus?",
            options = listOf("Ottoman Empire", "Roman Empire", "Persian Empire", "Byzantine Empire"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 115,
            text = "What is the largest desert on Earth by surface area (including polar deserts)?",
            options = listOf("Sahara Desert", "Gobi Desert", "Arabian Desert", "Antarctic Desert"),
            correctAnswerIndex = 3,
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = 116,
            text = "Who discovered penicillin in 1928, ushering in the era of modern antibiotics?",
            options = listOf("Louis Pasteur", "Alexander Fleming", "Robert Koch", "Edward Jenner"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.MEDIUM
        ),

        // ==========================================
        // HARD: Advanced science, world history, and deep trivia
        // ==========================================
        Question(
            id = 201,
            text = "Which fundamental subatomic particle was confirmed in 2012 by experiments at CERN's Large Hadron Collider?",
            options = listOf("Tau neutrino", "Top quark", "Higgs boson", "Gluon"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 202,
            text = "In which year did the Fall of Constantinople to the Ottoman Empire mark the end of the Byzantine Empire?",
            options = listOf("1204", "1453", "1492", "1517"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 203,
            text = "Which planet in the Solar System rotates clockwise (retrograde) on its axis, opposite to most other planets?",
            options = listOf("Mercury", "Venus", "Mars", "Neptune"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 204,
            text = "Which landmark English legal charter signed in 1215 established the principle that everyone, even the king, is subject to the law?",
            options = listOf("Bill of Rights", "Magna Carta", "Treaty of Utrecht", "Petition of Right"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 205,
            text = "What is the heaviest naturally occurring chemical element found in significant primordial quantities?",
            options = listOf("Lead", "Radium", "Uranium", "Plutonium"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 206,
            text = "Which 1648 peace treaties brought an end to the Thirty Years' War and laid the foundation for the modern nation-state system?",
            options = listOf("Peace of Westphalia", "Treaty of Versailles", "Congress of Vienna", "Treaty of Tordesillas"),
            correctAnswerIndex = 0,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 207,
            text = "What major evolutionary event around 541 million years ago saw the rapid appearance of most major animal phyla?",
            options = listOf("Devonian Explosion", "Cambrian Explosion", "Permian Transition", "Triassic Radiation"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 208,
            text = "Who was the first emperor of a unified China, renowned for his Terracotta Army and standardization of weights and measures?",
            options = listOf("Han Wudi", "Qin Shi Huang", "Tang Taizong", "Kublai Khan"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 209,
            text = "In astrophysics, what is the boundary surface around a black hole beyond which neither light nor matter can escape?",
            options = listOf("Photon sphere", "Ergosphere", "Event horizon", "Accretion boundary"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 210,
            text = "Which ancient Babylonian king is famous for establishing one of the earliest comprehensive written legal codes?",
            options = listOf("Gilgamesh", "Hammurabi", "Nebuchadnezzar II", "Sargon of Akkad"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 211,
            text = "What is the lowest land-based elevation on the surface of the Earth?",
            options = listOf("Death Valley floor", "Danakil Depression", "Dead Sea shoreline", "Lake Eyre basin"),
            correctAnswerIndex = 2,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 212,
            text = "In cellular biochemistry, which nitrogenous base is present in RNA but substituted by Thymine in DNA?",
            options = listOf("Adenine", "Guanine", "Cytosine", "Uracil"),
            correctAnswerIndex = 3,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 213,
            text = "Which 1494 treaty divided the lands of the newly explored non-European world between Portugal and the Crown of Castile?",
            options = listOf("Treaty of Tordesillas", "Treaty of Utrecht", "Treaty of Paris", "Treaty of Zaragoza"),
            correctAnswerIndex = 0,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 214,
            text = "What relic thermal radiation from the early universe fills all space with an average temperature of approximately 2.725 Kelvin?",
            options = listOf("Cosmic Microwave Background (CMB)", "Hawking Radiation", "Synchrotron Glow", "Cherenkov Emission"),
            correctAnswerIndex = 0,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 215,
            text = "The ancient intellectual center of the Library of Alexandria flourished under which Greek dynasty in Egypt?",
            options = listOf("Seleucid dynasty", "Ptolemaic dynasty", "Antigonid dynasty", "Achaemenid dynasty"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.HARD
        ),
        Question(
            id = 216,
            text = "What is the temperature of absolute zero, where thermodynamic particle entropy reaches its minimum, in degrees Celsius?",
            options = listOf("-250.00°C", "-273.15°C", "-299.79°C", "-315.25°C"),
            correctAnswerIndex = 1,
            difficulty = Difficulty.HARD
        )
    )

    fun getAllQuestions(): List<Question> = questions
}
