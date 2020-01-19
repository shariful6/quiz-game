package com.shkhan.jobnpxs;

public class QuestionsLibrary {

    private String mQuestions[]={
            "Which is the largest land animal in the world?",
            "Which is the highest mountain peak in the world?",
            "Which is the longest river in the world?",
            "Which is the largest country in the world by area?",
            "Which is the largest ocean in the world?",
            "Which is the largest continent by area?",
            "Which country/region has the highest population density in the world?",
            "Which country has the highest populations?",
            "Which is the tallest building in the world?",
            "Which is the longest train road in the world?",
            "Which is the largest fresh water lake in the world?",
            "Which is the largest city in the world by area?",
            "Which is the coldest inhabited place in the world?",
            "Which is the hottest inhabited place on earth?",
            "Which airport is located at the highest altitude?",
            "Which city has the highest population in the world?",
            "Largest living creature in world?",
            "Largest salt water lake in world?",
            "Which is the largest library in the world?",
            "Which place has the highest average rainfall in the world?",
            "Which is the largest bird in the world?",
            "Which is the smallest bird in the world?",
            "Which is the smallest country in the world?",
            "Which is the smallest continent?",
            "How many planets are there in our solar system?",
            "Which planet is also known as the Red planet?",
            "Which planet has maximum moons or natural satellites?",
            "Which member of our solar system has the largest size?",
            "Which is the smallest planet in our solar system?",
            "Which planet is closest to the Sun?",
            "How many stars are there in our solar system?",
            "Pluto is classified as a",
            "Which planet's ring system can be easily observed from the Earth?",
            "Which is the closest star outside our solar system?",
            "The third closest planet to Sun is",
            "Which is the largest planet of our solar system?",
            "The farthest planet from Sun in our Solar system is",
            "Which country hosted 2016 Olympic games ?",
            "Which was the first country to launch Artificial satellite in the space.?",
            "Which continent does Brazil lie in ?",
            "National amblem of which of these countries shows a Lion and sword.?",
            "Which of the following companies is the world's largest maker of computer chips ?",
            "Mercedes-Benz has it's headquarter in",
            "The first spacecraft to reach Mars was-",
            "Who was the first woman cosmonaut?",
            "Who was the first man to fly into space ?",
            "What was the name of the spacecraft, that landed the first humans on moon ?",
            "Neil Armstrong and Buzz Aldrin became the first humans to land on moon, on",
            "GPS is space based satellite navigation system. GPS stands for",
            "An instrument for measuring time accurately in spite of motion or variations in temperature, humidity, and air pressure is known as",
            "The branch of medicine that deals with the anatomy, physiology and diseases of the eyeball and orbit is known as"

    };

    private String mChoice[][]={
            {"Elephant","Giraffe","Camel","Hippo"},
            {"k2","Kanchenjunga","Mount Everest","Lhotse"},
            {"Amazon","Nile","Yangtze","Brahamputra"},
            {"China","Canada","United States of America","Russia"},
            {"pacific ocean","Indian ocean","Atlantic ocean","Arctic ocean"},
            {"Africa","Asia","Europe","North America"},
            {"Monaco","Singapore","Macau","India"},
            {"India","China","United States of America","Russia"},
            {"Eiffel tower","Sanghai tower","petronas twin towers","burj Khalifa"},
            {"Moscow-Pyongyang","Moscow-Vladivostok","Moscow-Beijing","Toronto-Vancouver"},
            {"Lake Superior","Lake Huron","Lake Victoria","Lake Baikal"},
            {"New York","Moscow","New Delhi","Paris"},
            {"Oymyakon,Russia","Toronto,Canada","Moscow, Russia","Antarctica"},
            {"CERN, Geneva.","Lut Desert, Iran","Bangkok, Thailand."," Dallol, Ethopia"},
            {"Bangda airport, Tibet","Daocheng Yading airport, China","Leh airport, India","Bagdogra airport, India"},
            {" Mumbai - India","New York - United States","London - United Kingdom"," Tokyo - Japan"},
            {" Elephant","Giraffe","Blue Whale","Camel"},
            {"Sambhar lake","Utah lake","Caspian sea","Lake urmia"},
            {"British-Indian library","Library and Archives","Library of Congress"," Russian state library "},
            {"Lloro, Colombia","Lopez de Micay, Colombia","Cherrapunzi, India","Mawsynram, India"},
            {"Emu","Northern cassowary","Southern cassowary","Ostrich"},
            {"The Weebill","The Pardalote","The Bee hummingbird","The Goldcrest"},
            {"Vatican","Monaco","Nauru","San Marino"},
            {"Asia","Nort America","South America","Australia"},
            {"10","9","8","7"},
            {"Mars","Earth","Saturn","Jupitor"},
            {"Saturn","Jupitor","Uranus","Neptune"},
            {"Jupitor","Saturn","Earth","Sun"},
            {"Mercury","Mars","Jupitor"," Pluto"},
            {"Marse","Venus","Marcury","Earth"},
            {"9","8","1","None"},
            {"Planet","Dwarf Planet","Star","Asteroid"},
            {"Jupitor","Venus","Uranus","Saturn"},
            {"Alpha Centauri A","Proxima Centauri","Pole Star"," North Star"},
            {"Venus","Earth","Mars","Mercury"},
            {"Saturn","Jupitor","Earth","Mars"},
            {"Jupitor","Saturn","Uranus","Neptune"},
            {"India","Russia","Brazil"," France"},
            {"Russia","U.S.A","Germany","Japan"},
            {"South America","North America","Africa","Europe"},
            {"India","South Africa","Nepal","Sri Lanka"},
            {"Intel","Microsoft","AMD","None of these."},
            {"U.S.A","U.K","Germany","Japan"},
            {"Sputnik","Appolo-12","GSLV-5","Viking-1"},
            {"Margaret Thatcher (U.K.)","Junko Tabei (Japan)","Sunita Williams (India)","Velentina Tereshkova (Russia)"},
            {"Yuri Gagarin (Russia)","Rakesh Sharma (India)","Neil Alden Armstrong (U.S.A)","Michael Collins (U.S.A)"},
            {"Apollo 9","Apollo 10","Apollo 11","Sputnik"},
            {"October 26, 1979","June 12, 1980","April 12, 1986","July 20, 1969"},
            {"Global positioning system","Global pointer satellite","General positioning system","eneral purpose satellite system"},
            {"Swiss watch","Space watch","Chronometer","Barometer"},
            {"Physiology","Ophthalmology","Neurology","None of these."}
    };
    private String mCorrectAns[]={"Elephant","Mount Everest","Nile","Russia","pacific ocean",
            "Asia","Macau","China","Burj Khalifa","Moscow-Pyongyang",
            "Lake Superior","New York","Oymyakon,Russia"," Dallol, Ethopia","Daocheng Yading airport, China",
            "Tokyo - Japan","Blue Whale","Caspian sea","Library of Congress ","Mawsynram, India",
            "Ostrich","The Bee hummingbird","Vatican","Australia","8","Mars","Jupitor","Sun","Mercury","Mercury",
            "1","Dwarf Planet","Saturn","Alpha Centauri A","Earth","Jupitor","Neptune","Brazil","Russia","South America",
            "Sri Lanka","Intel","Germany","Viking-1","Velentina Tereshkova (Russia)","Yuri Gagarin (Russia)","Apollo 11","July 20, 1969","Global positioning system","Chronometer",
            "Ophthalmology"

    };

    public String getQuestion (int a)
    {
        String question=mQuestions[a];
        return question;
    }

    public String getChoice1(int a)
    {
        String choice0=mChoice[a][0];
        return choice0;
    }
    public String getChoice2(int a)
    {
        String choice1=mChoice[a][1];
        return choice1;
    }

    public String getChoice3(int a)
    {
        String choice2=mChoice[a][2];
        return choice2;
    }

    public String getChoice4(int a)
    {
        String choice3=mChoice[a][3];
        return choice3;
    }

    public  String getCorrectAnswer(int a)
    {
        String answer=mCorrectAns[a];
        return answer;
    }




}
