package com.example.yemekproje;

import java.util.ArrayList;
import java.util.List;

public class RecipeData {

    public static List<Recipe> getStaticRecipes() {
        List<Recipe> list = new ArrayList<>();
        list.addAll(getCorbalar());
        list.addAll(getAnaYemekler());
        list.addAll(getSalatalar());
        list.addAll(getAraSicaklar());
        list.addAll(getTatlilar());
        return list;
    }

    public static List<Recipe> getCorbalar() {
        List<Recipe> list = new ArrayList<>();
        Recipe r1 = new Recipe("Mercimek Çorbası", "30 dk | 150 Kalori", "ic_merco",
                "• 1.5 su bardağı kırmızı mercimek\n• 1 adet orta boy kuru soğan\n• 1 adet orta boy havuç\n• 1 adet patates\n• 1 yemek kaşığı domates salçası\n• 2 yemek kaşığı tereyağı\n• 6-7 su bardağı sıcak su\n• Yarım tatlı kaşığı tuz\n• 1 çay kaşığı karabiber\n• 1 tatlı kaşığı kuru nane\n• Yarım tatlı kaşığı pul biber",
                "1. Kırmızı mercimeği bir süzgece alıp berrak suyu çıkana kadar iyice yıkayın ve süzün.\n2. Kuru soğanı yemeklik küçük küçük doğrayın. Havuç ve patatesin kabuklarını soyup rendeleyin.\n3. Geniş bir tencerede tereyağını eritip doğradığınız soğanları hafif pembeleşene kadar kavurun.\n4. Üzerine rendelenmiş havuç ve patatesi ekleyip sebzelerin aroması çıkana kadar 2-3 dakika daha soteleyin.\n5. Domates salçasını ilave edip çiğ kokusu gidene kadar karıştırarak kavurmaya devam edin.\n6. Süzdüğünüz mercimeği tencereye ekleyin, birkaç tur karıştırıp üzerine sıcak suyu ilave edin.\n7. Tuz ve karabiberi ekledikten sonra tencerenin kapağını kapatıp mercimekler ve sebzeler tamamen yumuşayana kadar yaklaşık 25 dakika pişirin.\n8. Pişen çorbayı ocaktan alın ve pürüzsüz bir kıvam elde edene kadar blenderdan geçirin.\n9. Küçük bir sos tavasında tereyağı ile pul biber ve naneyi kızdırıp çorbanın üzerine gezdirerek sıcak servis yapın.");
        r1.setVideoUrl("https://www.youtube.com/watch?v=J_mB8H7F4Qc");
        r1.setCategory("Çorbalar");
        r1.setMainIngredient("mercimek, soğan, havuç, patates, salça, tereyağı, nane, pul biber, tuz, karabiber, su");
        list.add(r1);

        Recipe r2 = new Recipe("Yayla Çorbası", "20 dk | 120 Kalori", "ic_yayla",
                "• 1 çay bardağı pirinç\n• 2 su bardağı yoğurt\n• 1 adet yumurta sarısı\n• 1 yemek kaşığı un\n• 6 su bardağı sıcak su\n• 2 yemek kaşığı tereyağı\n• 1 yemek kaşığı kuru nane\n• Yarım tatlı kaşığı tuz",
                "1. Pirinçleri yıkayıp tencereye alın ve üzerini 2-3 bardak suyla tamamlayıp pirinçler iyice yumuşayana kadar haşlayın.\n2. Ayrı bir derin kasede yoğurt, un ve yumurta sarısını hiçbir topak kalmayacak şekilde pürüzsüzce çırpın.\n3. Haşlanan pirinçlerin suyundan bir kepçe alıp yoğurtlu karışıma yavaşça ekleyerek terbiyeyi ılıştırın.\n4. Terbiyeyi tencereye yavaşça dökerken bir yandan da çorbayı hızlıca karıştırın ki yoğurt kesilmesin.\n5. Çorba kaynamaya başlayana kadar sürekli aynı yöne doğru karıştırmaya devam edin.\n6. Kaynadıktan sonra altını kısın ve 5 dakika daha pişirin. En son tuzu ekleyin (erken eklenirse yoğurt kesilebilir).\n7. Küçük bir tavada tereyağını eritip naneyi içinde yakın ve çorba kaselerine servis ederken üzerine gezdirin.");
        r2.setVideoUrl("https://www.youtube.com/watch?v=9jDAsYhYkP8");
        r2.setCategory("Çorbalar");
        r2.setMainIngredient("pirinç, yoğurt, yumurta, un, su, tereyağı, nane, tuz");
        list.add(r2);

        Recipe r3 = new Recipe("Ezogelin Çorbası", "45 dk | 180 Kalori", "ic_ezo",
                "• 1 su bardağı kırmızı mercimek\n• 1 yemek kaşığı pilavlık bulgur\n• 1 yemek kaşığı pirinç\n• 1 adet kuru soğan\n• 2 diş sarımsak\n• 1 yemek kaşığı domates salçası\n• 1 yemek kaşığı biber salçası\n• 2 yemek kaşığı tereyağı\n• 1 tatlı kaşığı kuru nane\n• 1 çay kaşığı pul biber\n• Tuz, karabiber",
                "1. Mercimek, pirinç ve bulguru güzelce yıkayıp suyunu süzün.\n2. Tencerede tereyağını eritip yemeklik doğranmış soğan ve sarımsakları kokusu çıkana kadar şeffaf bir hal alana dek kavurun.\n3. Domates ve biber salçalarını ekleyip birkaç dakika daha kavurarak aromayı güçlendirin.\n4. Yıkanmış bakliyatları tencereye ekleyip karıştırın, ardından yaklaşık 7-8 su bardağı sıcak suyu ilave edin.\n5. Bakliyatlar iyice yumuşayıp çorba kıvam alana kadar yaklaşık 40-45 dakika kısık ateşte pişirin.\n6. Çorba piştiğinde tuz, karabiber, bolca nane ve pul biberi ekleyip karıştırın.\n7. İsteğe bağlı olarak hafifçe blenderdan geçirebilirsiniz (geleneksel hali hafif taneli olur).");
        r3.setVideoUrl("https://www.youtube.com/watch?v=5Ue6-tWfG6U");
        r3.setCategory("Çorbalar");
        r3.setMainIngredient("mercimek, bulgur, pirinç, soğan, sarımsak, salça, tereyağı, nane, pul biber, tuz, karabiber");
        list.add(r3);

        Recipe rDomates = new Recipe("Domates Çorbası", "45 dk | 110 Kalori", "ic_dmts", 
                "• 5 adet olgun domates\n• 2 yemek kaşığı un\n• 2 yemek kaşığı tereyağı\n• 1 yemek kaşığı domates salçası\n• 1 su bardağı süt\n• 4 su bardağı sıcak su\n• Tuz, karabiber\n• Üzeri için rendelenmiş kaşar peyniri", 
                "1. Domatesleri rendeleyin veya mutfak robotunda pürüzsüz hale getirin.\n2. Tereyağını tencerede eritip unu ekleyin ve unun kokusu çıkıp rengi hafifçe dönene kadar kavurun.\n3. Salçayı ekleyip karıştırın, ardından hazırladığınız domatesleri ilave edin.\n4. Sıcak suyu azar azar ekleyip bir yandan çırpma teliyle hızlıca karıştırın ki topaklanma olmasın.\n5. Çorba kaynamaya başlayınca kısık ateşte domateslerin lezzeti oturana kadar 15 dakika pişirin.\n6. Son aşamada sütü yavaşça ekleyip bir taşım daha kaynatın. Tuz ve karabiberi ekleyip ocaktan alın.\n7. Servis ederken üzerine bolca rendelenmiş kaşar peyniri serperek sıcak sunum yapın.");
        rDomates.setVideoUrl("https://www.youtube.com/watch?v=vV99E7_8fFk");
        rDomates.setCategory("Çorbalar");
        rDomates.setMainIngredient("domates, un, tereyağı, salça, süt, tuz, karabiber, kaşar");
        list.add(rDomates);

        Recipe rMantar = new Recipe("Kremalı Mantar Çorbası", "45 dk | 180 Kalori", "ic_mantar", 
                "• 400 gram kültür mantarı\n• 1 adet kuru soğan\n• 2 yemek kaşığı tereyağı\n• 2 yemek kaşığı un\n• 1 paket sıvı krema (200 ml)\n• 4 su bardağı sıcak su\n• Tuz, karabiber\n• Bir tutam taze maydanoz", 
                "1. Mantarları nemli bir bezle silin ve ince ince dilimleyin. Soğanı çok küçük küpler halinde doğrayın.\n2. Tereyağını tencerede eritip soğanları hafifçe soteleyin.\n3. Mantarları ekleyin ve yüksek ateşte suyunu salıp çekene kadar sotelemeye devam edin.\n4. Unu ekleyip unun kokusu çıkana kadar 2 dakika daha kavurun.\n5. Sıcak suyu yavaş yavaş ekleyerek topaklanmaması için hızlıca karıştırın.\n6. Çorba kaynamaya başlayınca altını kısın ve 15 dakika pişirin.\n7. Son olarak kremayı, tuzu ve karabiberi ekleyin. 2-3 dakika daha kısık ateşte tutup ocaktan alın.\n8. İnce kıyılmış maydanoz ile süsleyerek servis edin.");
        rMantar.setVideoUrl("https://www.youtube.com/watch?v=uKofVat_GWA");
        rMantar.setCategory("Çorbalar");
        rMantar.setMainIngredient("mantar, soğan, tereyağı, un, krema, su, tuz, karabiber, maydanoz");
        list.add(rMantar);
        return list;
    }

    public static List<Recipe> getAnaYemekler() {
        List<Recipe> list = new ArrayList<>();
        Recipe r4 = new Recipe("Karnıyarık", "50 dk | 350 Kalori", "ic_karniyarik", 
                "• 6 adet orta boy patlıcan\n• 300 gram orta yağlı kıyma\n• 2 adet kuru soğan\n• 2 adet domates\n• 3 adet yeşil sivri biber\n• 3 diş sarımsak\n• 1 yemek kaşığı salça\n• Sıvı yağ, tuz, karabiber, pul biber\n• 1 su bardağı sıcak su", 
                "1. Patlıcanları alacalı (pijama şeklinde) soyun ve acısını alması için tuzlu suda 20 dakika bekletin.\n2. Kuruladığınız patlıcanları kızgın yağda her tarafı hafifçe yumuşayana kadar kızartın ve kağıt havlu serili bir tabağa alın.\n3. İç harcı için; soğanları yemeklik doğrayıp kavurun, kıymayı ekleyip suyunu çekene kadar pişirin.\n4. Küçük doğranmış yeşil biber, sarımsak, küp doğranmış domates ve baharatları ekleyip sotelemeye devam edin.\n5. Kızaran patlıcanları fırın tepsisine dizin, ortalarını bıçakla yarın ve hazırladığınız kıymalı harcı bolca doldurun.\n6. Üzerlerine birer dilim domates ve biber yerleştirin. Salçalı su karışımını tepsiye dökün.\n7. 190 derece önceden ısıtılmış fırında yaklaşık 25-30 dakika boyunca sebzeler kızarana kadar pişirin.");
        r4.setVideoUrl("https://www.youtube.com/watch?v=iI7SclX8zsc");
        r4.setCategory("Ana Yemekler");
        r4.setMainIngredient("patlıcan, kıyma, soğan, domates, biber, sarımsak, salça, sıvı yağ, tuz, karabiber, pul biber, su");
        list.add(r4);
        
        Recipe rSote = new Recipe("Tavuk Sote", "35 dk | 280 Kalori", "ic_tsote", 
                "• 600 gram tavuk göğsü (kuşbaşı doğranmış)\n• 2 yemek kaşığı sıvı yağ\n• 1 yemek kaşığı tereyağı\n• 1 adet kuru soğan\n• 2 adet yeşil biber\n• 1 adet kapya biber\n• 2 adet domates\n• 1 tatlı kaşığı domates salçası\n• Tuz, karabiber, kekik, pul biber", 
                "1. Tavukları geniş bir tavaya alın ve yüksek ateşte suyunu salıp tekrar çekene kadar ara ara karıştırarak pişirin.\n2. Tavuklar suyunu çekince sıvı yağı ve tereyağını ekleyin, yemeklik doğranmış soğanları ilave edip pembeleşene kadar kavurun.\n3. İnce dilimlenmiş yeşil ve kapya biberleri ekleyerek biberler hafifçe yumuşayana kadar soteleme işlemine devam edin.\n4. Salçayı ekleyip bir dakika kavurduktan sonra küp küp doğranmış domatesleri tencereye alın.\n5. Domatesler yumuşamaya başladığında tuz ve tüm baharatları ekleyin.\n6. Yarım çay bardağı kadar sıcak su ilave edip kapağını kapatın ve kısık ateşte tavuklar tamamen pişene kadar 10-12 dakika daha pişirin.");
        rSote.setVideoUrl("https://www.youtube.com/watch?v=H7G3v-k8jO4");
        rSote.setCategory("Ana Yemekler");
        rSote.setMainIngredient("tavuk, sıvı yağ, tereyağı, soğan, biber, domates, salça, tuz, karabiber, kekik, pul biber");
        list.add(rSote);

        Recipe rHunkar = new Recipe("Hünkar Beğendi", "60 dk | 420 Kalori", "ic_hunkar", 
                "• Eti için: 500 gr kuzu kuşbaşı, 1 soğan, 1 yk salça, 2 domates, 2 biber, 1 yk tereyağı\n• Beğendisi için: 3 adet büyük bostan patlıcanı, 1.5 yemek kaşığı un, 1.5 su bardağı süt, 1 yemek kaşığı tereyağı, yarım su bardağı rendelenmiş kaşar peyniri, tuz, muskat rendesi", 
                "1. Etleri tencereye alın, suyunu çekene kadar pişirin. Ardından tereyağı ve doğranmış soğanları ekleyip kavurun.\n2. Biberleri ve salçayı ekleyip soteledikten sonra küp doğranmış domatesleri ilave edin. Sıcak su ekleyip etler yumuşayana kadar pişirin.\n3. Patlıcanları közleyin, kabuklarını soyup ince ince kıyın.\n4. Beğendi için; tereyağında unu kavurun, sütü azar azar ekleyip koyulaşana kadar karıştırarak beşamel sos hazırlayın.\n5. Közlenmiş patlıcanları ve kaşar rendesini sosa ekleyip iyice karıştırın. Tuz ve muskat ekleyin.\n6. Beğendiyi tabağa yayın, üzerine etleri koyarak servis yapın.");
        rHunkar.setVideoUrl("https://www.youtube.com/watch?v=0hKst3FhM0I");
        rHunkar.setCategory("Ana Yemekler");
        rHunkar.setMainIngredient("kuzu, soğan, salça, domates, biber, tereyağı, patlıcan, un, süt, kaşar, tuz, muskat");
        list.add(rHunkar);

        Recipe rIzmir = new Recipe("İzmir Köfte", "45 dk | 310 Kalori", "ic_izmir", 
                "• Köftesi için: 500 gr kıyma, 1 soğan, 1 yumurta, yarım bardak galeta unu, maydanoz, tuz, karabiber, kimyon\n• 4 adet patates, 2 biber, 2 domates\n• Sosu: 1 yk salça, 2 bardak sıcak su", 
                "1. Köfte malzemelerini bir kapta toplayıp iyice yoğurun ve uzun ince şekiller verin.\n2. Patatesleri elma dilimi doğrayın.\n3. Patatesleri ve köfteleri kızgın yağda hafifçe kızartın.\n4. Fırın tepsisine bir patates bir köfte olacak şekilde dizin. Aralara biber ve domates koyun.\n5. Salçalı sıcak suyu üzerine gezdirin.\n6. 200 derece fırında 30-35 dakika kadar pişirin.");
        rIzmir.setVideoUrl("https://www.youtube.com/watch?v=M5FvFmI6Nvg");
        rIzmir.setCategory("Ana Yemekler");
        rIzmir.setMainIngredient("kıyma, soğan, yumurta, galeta unu, maydanoz, tuz, karabiber, kimyon, patates, biber, domates, salça, su");
        list.add(rIzmir);

        Recipe rFasulye = new Recipe("Taze Fasulye", "40 dk | 160 Kalori", "ic_fasulye", 
                "• 1 kg taze fasulye\n• 2 adet kuru soğan\n• 3 adet domates\n• Yarım çay bardağı zeytinyağı\n• 1 tatlı kaşığı toz şeker, tuz\n• 1 su bardağı sıcak su", 
                "1. Fasulyeleri ayıklayın, kılçıklarını temizleyin ve boyuna ikiye bölün.\n2. Soğanları yemeklik doğrayıp zeytinyağında pembeleşene kadar kavurun.\n3. Domateslerin kabuklarını soyup doğrayın ve soğanlara ekleyin.\n4. Fasulyeleri ilave edip üzerine tuzu ve şekeri serpin. Kapağını kapatıp sararana kadar 10 dakika pişirin.\n5. Sıcak suyunu ekleyip fasulyeler yumuşayana kadar yaklaşık 30-35 dakika pişirin.");
        rFasulye.setVideoUrl("https://www.youtube.com/watch?v=F_Y2YhYm-c8");
        rFasulye.setCategory("Ana Yemekler");
        rFasulye.setMainIngredient("taze fasulye, soğan, domates, zeytinyağı, şeker, tuz, su");
        list.add(rFasulye);
        return list;
    }

    public static List<Recipe> getSalatalar() {
        List<Recipe> list = new ArrayList<>();
        Recipe rMevsim = new Recipe("Mevsim Salatası", "15 dk | 80 Kalori", "ic_mevsim", 
                "• Yarım adet göbek marul\n• 2 adet büyük havuç\n• Çeyrek mor lahana\n• 5-6 dal maydanoz\n• Sos: Yarım limon suyu, 3 yk zeytinyağı, 1 yk elma sirkesi, tuz", 
                "1. Göbek marulu yıkayıp kuruladıktan sonra ince şeritler halinde doğrayın ve geniş bir salata kasesine alın.\n2. Havuçların kabuklarını soyup rendenin iri tarafıyla rendeleyin.\n3. Mor lahanayı çok ince kıyın, bir miktar tuzla ovarak yumuşamasını sağlayın.\n4. Maydanozları ince ince kıyın. Tüm sebzeleri kasede birleştirin.\n5. Sos malzemelerini karıştırıp salatanın üzerine dökün ve iyice harmanlayarak servis edin.");
        rMevsim.setVideoUrl("https://www.youtube.com/watch?v=oV8p3_r89Lw");
        rMevsim.setCategory("Salatalar");
        rMevsim.setMainIngredient("marul, havuç, mor lahana, maydanoz, limon, zeytinyağı, sirke, tuz");
        list.add(rMevsim);

        Recipe rCoban = new Recipe("Çoban Salatası", "10 dk | 90 Kalori", "ic_coban", 
                "• 3 adet domates\n• 2 adet salatalık\n• 2 adet yeşil sivri biber\n• 1 adet kuru soğan\n• Yarım demet maydanoz\n• Sos: Yarım limon suyu, 4 yk zeytinyağı, tuz", 
                "1. Domateslerin kabuklarını soyun ve tavla zarı büyüklüğünde küp küp doğrayın.\n2. Salatalıkları alacalı soyup aynı domatesler gibi küçük küpler halinde hazırlayın.\n3. Biberleri çekirdeklerini temizledikten sonra ince halkalar şeklinde doğrayın.\n4. Soğanı ve maydanozu incecik kıyın. Tüm malzemeleri derin bir salata kabında birleştirin.\n5. Zeytinyağı, limon ve tuzu ekleyip sebzeleri ezmeden karıştırın.");
        rCoban.setVideoUrl("https://www.youtube.com/watch?v=KzL7_q7-fMo");
        rCoban.setCategory("Salatalar");
        rCoban.setMainIngredient("domates, salatalık, biber, soğan, maydanoz, limon, zeytinyağı, tuz");
        list.add(rCoban);

        Recipe rSezar = new Recipe("Sezar Salata", "20 dk | 250 Kalori", "ic_sezar", 
                "• 1 adet göbek marul\n• 200 gram tavuk göğsü\n• Kruton ekmekler\n• Parmesan peyniri\n• Sos: 3 yk mayonez, 1 diş sarımsak, 1 tatlı kaşığı hardal, yarım limon suyu", 
                "1. Tavukları ızgarada pişirin ve ince şeritler halinde dilimleyin.\n2. Marulları elinizle kopararak geniş bir kaba alın.\n3. Krutonları hazırlamak için ekmekleri küp doğrayıp fırınlayın.\n4. Sos malzemelerini pürüzsüz olana kadar çırpın ve marullarla harmanlayın.\n5. Üzerine tavukları, krutonları ve peynir rendesini ekleyip servis yapın.");
        rSezar.setVideoUrl("https://www.youtube.com/watch?v=0W8N3yS0Y-w");
        rSezar.setCategory("Salatalar");
        rSezar.setMainIngredient("marul, tavuk, ekmek, parmesan, mayonez, sarımsak, hardal, limon");
        list.add(rSezar);

        Recipe rGavurdağı = new Recipe("Gavurdağı Salatası", "25 dk | 180 Kalori", "ic_gavurdagi", 
                "• 4 adet domates\n• 2 adet yeşil biber\n• 1 adet kuru soğan\n• 1 su bardağı ceviz içi\n• Sos: 4 yk nar ekşisi, 4 yk zeytinyağı, 1 tatlı kaşığı sumak, tuz", 
                "1. Domates, biber ve soğanı olabildiğince küçük (zar kadar) doğrayın.\n2. Cevizleri bıçakla irice kıyın.\n3. Nar ekşisi, zeytinyağı ve baharatlarla sosu hazırlayın.\n4. Tüm malzemeyi karıştırıp en üste kalan cevizleri serperek servis edin.");
        rGavurdağı.setVideoUrl("https://www.youtube.com/watch?v=F_f8r0q7C8A");
        rGavurdağı.setCategory("Salatalar");
        rGavurdağı.setMainIngredient("domates, biber, soğan, ceviz, nar ekşisi, zeytinyağı, sumak, tuz");
        list.add(rGavurdağı);
        return list;
    }

    public static List<Recipe> getAraSicaklar() {
        List<Recipe> list = new ArrayList<>();
        Recipe rPacanga = new Recipe("Paçanga Böreği", "30 dk | 320 Kalori", "ic_pacanga", 
                "• 2 adet taze yufka\n• 150 gram pastırma\n• 1.5 su bardağı rendelenmiş kaşar peyniri\n• 2 adet domates, 2 adet yeşil biber\n• Kızartmak için sıvı yağ", 
                "1. Yufkaları 8 eşit üçgen parça elde edecek şekilde bölün.\n2. Pastırmaları, doğranmış domatesleri ve biberleri bir kapta karıştırın.\n3. Yufkanın geniş kısmına harçtan koyup üzerine kaşar ekleyin.\n4. Kenarlarını kapatıp gevşek bir rulo yapın.\n5. Kızgın yağda arkalı önlü kızartıp sıcak servis yapın.");
        rPacanga.setVideoUrl("https://www.youtube.com/watch?v=O1S_T8r5f-M");
        rPacanga.setCategory("Ara Sıcaklar");
        rPacanga.setMainIngredient("yufka, pastırma, kaşar, domates, biber, sıvı yağ");
        list.add(rPacanga);

        Recipe rIcli = new Recipe("İçli Köfte", "60 dk | 280 Kalori", "ic_iclikofte", 
                "• Dışı için: 2 su bardağı ince bulgur, yarım bardak irmik, 1 yumurta, 3 yk un, 1 yk salça\n• İçi için: 300 gr kıyma, 2 soğan, yarım bardak ceviz, baharatlar, nane", 
                "1. Kıymayı soğanla kavurun, ceviz ve baharatları ekleyip soğumaya bırakın.\n2. Bulgur ve irmiği ıslatıp şişince diğer dış malzemelerle iyice yoğurun.\n3. Hamurdan parçalar koparıp ortasını oyun, iç harcı doldurup kapatın.\n4. Bol kızgın yağda altın sarısı olana kadar kızartarak servis yapın.");
        rIcli.setVideoUrl("https://www.youtube.com/watch?v=uK79K_yX-N8");
        rIcli.setCategory("Ara Sıcaklar");
        rIcli.setMainIngredient("bulgur, irmik, yumurta, un, salça, kıyma, soğan, ceviz, nane");
        list.add(rIcli);

        Recipe rMucver = new Recipe("Kabak Mücver", "40 dk | 150 Kalori", "ic_mucver", 
                "• 3 adet kabak\n• 2 adet yumurta\n• 1 su bardağı un\n• Yarım demet dereotu\n• 1 paket kabartma tozu, tuz, karabiber", 
                "1. Kabakları rendeleyin ve sularını iyice sıkın (sulu kalırsa un çok gider).\n2. Yumurta, un, dereotu ve baharatlarla kabakları karıştırın.\n3. Kaşık yardımıyla kızgın yağa döküp her iki tarafını da nar gibi kızartın.\n4. Sarımsaklı yoğurtla servis edin.");
        rMucver.setVideoUrl("https://www.youtube.com/watch?v=F_f8r0q7C8A");
        rMucver.setCategory("Ara Sıcaklar");
        rMucver.setMainIngredient("kabak, yumurta, un, dereotu, kabartma tozu, tuz, karabiber");
        list.add(rMucver);

        Recipe rSigara = new Recipe("Sigara Böreği", "25 dk | 210 Kalori", "ic_sigara", 
                "• 3 adet taze yufka\n• 250 gram lor peyniri\n• Yarım demet maydanoz\n• Kızartmak için sıvı yağ", 
                "1. Yufkaları 12 veya 16 eşit üçgen parçaya bölün.\n2. Peynir ve ince kıyılmış maydanozu bir kasede karıştırın.\n3. Yufkanın geniş kısmına harçtan koyup kenarları içe katlayarak rulo yapın.\n4. Ucunu suya batırıp yapıştırın ve kızgın yağda kızartın.");
        rSigara.setVideoUrl("https://www.youtube.com/watch?v=O1S_T8r5f-M");
        rSigara.setCategory("Ara Sıcaklar");
        rSigara.setMainIngredient("yufka, peynir, maydanoz, sıvı yağ");
        list.add(rSigara);

        Recipe rKısır = new Recipe("Kısır", "25 dk | 210 Kalori", "ic_iclikofte", 
                "• 2 su bardağı ince bulgur\n• 1.5 su bardağı sıcak su\n• 1 yk domates, 1 yk biber salçası\n• Yeşillikler (Taze soğan, maydanoz, nane)\n• Nar ekşisi, zeytinyağı, limon", 
                "1. Bulguru sıcak suyla ıslatıp ağzını kapatarak şişmesini bekleyin.\n2. Bulgur şişince salçalarla birlikte iyice ovun.\n3. İnce kıyılmış tüm yeşillikleri ve sosları ekleyip harmanlayın. Marul eşliğinde servis yapın.");
        rKısır.setVideoUrl("https://www.youtube.com/watch?v=F_Y2YhYm-c8");
        rKısır.setCategory("Ara Sıcaklar");
        rKısır.setMainIngredient("bulgur, su, salça, soğan, maydanoz, nane, nar ekşisi, zeytinyağı, limon");
        list.add(rKısır);
        return list;
    }

    public static List<Recipe> getTatlilar() {
        List<Recipe> list = new ArrayList<>();
        Recipe rSutlac = new Recipe("Fırın Sütlaç", "45 dk | 250 Kalori", "ic_sutlac", 
                "• 1 litre tam yağlı süt\n• 2 çay bardağı toz şeker\n• Yarım su bardağı pirinç\n• 2 yk nişasta\n• 1 paket vanilya\n• 2 bardak su", 
                "1. Pirinçleri suyla iyice yumuşayana kadar haşlayın.\n2. Süt ve şekeri ekleyip kaynamaya bırakın.\n3. Nişastayı sütle açıp tencereye ekleyin ve kıvam alana kadar pişirin.\n4. Güveç kaplarına paylaştırın.\n5. Fırın tepsisine su koyup güveçleri dizin ve üstleri kızarana kadar fırınlayın.");
        rSutlac.setVideoUrl("https://www.youtube.com/watch?v=u5u-V6I8Nms");
        rSutlac.setCategory("Tatlılar");
        rSutlac.setMainIngredient("süt, şeker, pirinç, nişasta, vanilya, su");
        list.add(rSutlac);

        Recipe rMagnolia = new Recipe("Çilekli Magnolia", "30 dk | 300 Kalori", "ic_magnolya", 
                "• 1 litre süt\n• 1 su bardağı şeker\n• 2 yk un, 2 yk nişasta\n• 1 adet yumurta sarısı\n• 1 paket sıvı krema\n• Bebe bisküvisi, çilek", 
                "1. Muhallebi malzemelerini (krema hariç) pişirip soğumaya bırakın.\n2. Soğuyan muhallebiye kremayı ekleyip mikserle çırpın.\n3. Bisküvileri rondodan geçirin.\n4. Kup bardaklarına bisküvi, meyve ve muhallebi şeklinde kat kat dizerek süsleyin.");
        rMagnolia.setVideoUrl("https://www.youtube.com/watch?v=pW8yv8C9_8k");
        rMagnolia.setCategory("Tatlılar");
        rMagnolia.setMainIngredient("süt, şeker, un, nişasta, yumurta, krema, bisküvi, çilek");
        list.add(rMagnolia);

        Recipe rRevani = new Recipe("Revani", "50 dk | 380 Kalori", "ic_revani", 
                "• Kek: 3 yumurta, 1 bardak şeker, 1 bardak irmik, 1 bardak un, 1 bardak yoğurt, kabartma tozu\n• Şerbet: 3 bardak şeker, 3.5 bardak su, yarım limon", 
                "1. Önce şerbeti kaynatıp soğumaya bırakın.\n2. Kek malzemelerini çırpıp 180 derecede pişirin.\n3. Kek fırından çıkınca ilk sıcaklığı geçince (yaklaşık 2 dk) soğuk şerbeti dökün.\n4. Dinlendikten sonra Hindistan cevizi ile süsleyip dilimleyerek servis edin.");
        rRevani.setVideoUrl("https://www.youtube.com/watch?v=mH5V-O8T6I8");
        rRevani.setCategory("Tatlılar");
        rRevani.setMainIngredient("yumurta, şeker, irmik, un, yoğurt, kabartma tozu, su, limon");
        list.add(rRevani);

        Recipe rMozaik = new Recipe("Mozaik Pasta", "20 dk | 320 Kalori", "ic_mozaik", 
                "• 2 paket pötibör bisküvi\n• 1 bardak süt\n• 3 yk kakao\n• 5 yk toz şeker\n• 100 gram tereyağı", 
                "1. Bisküvileri derin bir kasede çok ufalamadan iri parçalar halinde kırın.\n2. Süt, kakao, şeker ve eritilmiş tereyağını pürüzsüz olana kadar karıştırın.\n3. Karışımı bisküvilerin üzerine döküp bisküvileri ezmeden harmanlayın.\n4. Streç filme sarıp rulo veya piramit şekli verin ve dondurucuda 3 saat bekletin.");
        rMozaik.setVideoUrl("https://www.youtube.com/watch?v=O_h_k8-Q6X8");
        rMozaik.setCategory("Tatlılar");
        rMozaik.setMainIngredient("bisküvi, süt, kakao, şeker, tereyağı");
        list.add(rMozaik);
        return list;
    }
}
