/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namazvakti;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;//pencere nesnesi oluşturabilmemiz için içe aktarmamız gereken sınıf
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;//panel nesnesi oluşturabilmemiz için içe aktarmamız gereken sınıf
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
public class NamazVakti extends JFrame
{
    JFrame frame,frame_home,frame_gizle;
    JDialog f;
    JPanel panel,panel_home,panel_gizle;
    JLabel sehir,tarih,imsak,gunes,ogle,ikindi,aksam,yatsi,kalan;
    JButton vakital,sehirsec,goster,yenile;// butonlara verdigimiz isimler
    JComboBox<String> sec_il;
    String[] illiste = {"ADANA","ADIYAMAN","AFYONKARAHİSAR","AĞRI","AKSARAY","AMASYA","ANKARA","ANTALYA","ARDAHAN","ARTVİN","AYDIN","BALIKESİR","BARTIN","BATMAN","BAYBURT","BİLECİK","BİNGÖL","BİTLİS","BOLU","BURDUR","BURSA","ÇANAKKALE","ÇANKIRI","ÇORUM","DENİZLİ","DİYARBAKIR","DÜZCE","EDİRNE","ELAZIĞ","ERZİNCAN","ERZURUM","ESKİŞEHİR","GAZİANTEP","GİRESUN","GÜMÜŞHANE","HAKKARİ","HATAY","IĞDIR","ISPARTA","İSTANBUL","İZMİR","KAHRAMANMARAŞ","KARABÜK","KARAMAN","KARS","KASTAMONU","KAYSERİ","KİLİS","KIRIKKALE","KIRKLARELİ","KIRŞEHİR","KOCAELİ","KONYA","KÜTAHYA","MALATYA","MANİSA","MARDİN","MERSİN","MUĞLA","MUŞ","NEVŞEHİR","NİĞDE","ORDU","OSMANİYE","RİZE","SAKARYA","SAMSUN","ŞANLIURFA","SİİRT","SİNOP","ŞIRNAK","SİVAS","TEKİRDAĞ","TOKAT","TRABZON","TUNCELİ","UŞAK","VAN","YALOVA","YOZGAT","ZONGULDAK"};
    String appdata = System.getenv("APPDATA");
    String iconPath = appdata + "\\namazvakti\\icon.png";
    File icon = new File(iconPath);
    //buraya icon indirici koyulacak
    Elements e,e1;
    boolean vakitcek=false;
    String[] parts=new String[145];
   
   
   public boolean getTime(String il) throws IOException{
       String url="https://www.sabah.com.tr/"+Convert(il.toLowerCase()+"-namaz-vakitleri");
       Document doc=(Document) Jsoup.connect(url).get();
       
       e1=doc.select("td");
      
           System.out.println("İl:"+secilenil+" Vakitler çekildi: "+e1.text().toString());
           String str = secilenil+" "+e1.text();

        File file = new File("dosya.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file, false);
        BufferedWriter bWriter = new BufferedWriter(fileWriter);
        bWriter.write(str);
        bWriter.close();
           if(e1.text()!=""){
               return true;
           }
           else{
               return false;
           }
          
   }
   String secilenil=parts[0];
    public void sehirsec(){
        frame=new JFrame("Şehir Seç");
        // Paneli oluşturuyorum
        panel= new JPanel();
        panel.setLayout(null);
        
        //nesleri oluşturuyorum
        ImageIcon imgicon = new ImageIcon(iconPath);
        sec_il = new JComboBox(illiste);//.toArray());
        sec_il.setBounds(20, 20, 300, 30);
        sec_il.addActionListener (new ActionListener () {
    public void actionPerformed(ActionEvent e) {
        secilenil = String.valueOf(sec_il.getSelectedItem());
    }
});
        
        panel.add(sec_il);
        vakital=new JButton("Vakitleri Al");// buton
        vakital.setBounds(180, 70, 100, 50);
        vakital.setBackground(Color.white);//renk
        panel.add(vakital);
        yenile=new JButton("Yenile");// buton
        yenile.setBounds(60, 70, 100, 50);
        yenile.setBackground(Color.white);//renk
        panel.add(yenile);
        
         //paneli Frame e ekliyorum
        frame.add(panel); //---&gt;&gt;&gt;frame.add(panel) ile aynı anlamı taşır
        frame.setBounds(770, 400, 350, 200);
        frame.setVisible(true);
        frame.setResizable(false);//sabit boyutlar
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setIconImage(imgicon.getImage());
        

       
        vakital.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    if(getTime(secilenil)){
                        
                        System.out.println(secilenil);
                        vakitcek=true;
                        CloseFrame();
                        JOptionPane.showMessageDialog(null, "Vakitler alındı...","Mesaj",JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                        anasayfa();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(NamazVakti.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        yenile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
               sehirsec();
            }
        });
   }
     public void anasayfa() throws FileNotFoundException, IOException{
        frame_home=new JFrame("Namaz Vakitleri");
        // Paneli oluşturuyorum
        panel_home = new JPanel();
        panel_home.setLayout(null);
        String il="",tarh="",imsk="",gnes="",oglen="",ikind="",aksm="",yats="";
        if(oku())
        il=parts[0];tarh="Tarih: "+parts[1];imsk="İmsak: "+parts[2];gnes="Güneş: "+parts[3];oglen="Öğle: "+parts[4];ikind="İkindi: "+parts[5];aksm="Akşam: "+parts[6];yats="Yatsı: "+parts[7];    
        sehir=new JLabel(il);
        sehir.setFont(new java.awt.Font("Arial black", 1, 20));
        sehir.setBounds(20, 30, 150, 50);
        panel_home.add(sehir);
        tarih=new JLabel(tarh);
        tarih.setBounds(19, 70, 100, 50);
        panel_home.add(tarih);
        imsak=new JLabel(imsk);
        imsak.setBounds(20, 90, 100, 50);
        panel_home.add(imsak);
        gunes=new JLabel(gnes);
        gunes.setBounds(20, 110, 100, 50);
        panel_home.add(gunes);
        ogle=new JLabel(oglen);
        ogle.setBounds(20, 130, 100, 50);
        panel_home.add(ogle);
        ikindi=new JLabel(ikind);
        ikindi.setBounds(20, 150, 100, 50);
        panel_home.add(ikindi);
        aksam=new JLabel(aksm);
        aksam.setBounds(20, 170, 100, 50);
        panel_home.add(aksam);
        yatsi=new JLabel(yats);
        yatsi.setBounds(20, 190, 100, 50);
        panel_home.add(yatsi);
        
        
        //nesleri oluşturuyorum
        ImageIcon imgicon = new ImageIcon(iconPath);
        sehirsec=new JButton("Şehir Seç");// buton
        sehirsec.setBounds(270, 50, 100, 50);
        sehirsec.setBackground(Color.white);//renk
        panel_home.add(sehirsec);
        JMenuBar menu = new JMenuBar();
        menu.setBounds(0,0,500,30);
        JMenu sec = new JMenu("Ayarlar");
        menu.add(sec);
        JMenu info = new JMenu("Hakkında");
        menu.add(info);
        JMenuItem r1 = new JMenuItem("Şehir seç");
        sec.add(r1);
        JMenuItem guncelle = new JMenuItem("Güncelle");
        sec.add(guncelle);
        JMenuItem exit = new JMenuItem("Çıkış");
        sec.add(exit);
        
       if(vakitcek){
            frame_home.dispose();
            /*veriler.removeAll();
            kayitli=parts[0]+"\nTarih: "+parts[1]+"\nİmsak: "+parts[2]+"\n Güneş: "+parts[3]+"\nÖğle: "+parts[4]+"\nİkindi: "+parts[5]+"\nAkşam: "+parts[6]+"\nYatsı: "+parts[7];
            veriler=new JLabel(kayitli);*/
            frame_home.repaint();//frame yenileme
            frame_home.revalidate();//frame yenileme
       }
        
        panel_home.add(menu);
         //paneli Frame e ekliyorum
        frame_home.add(panel_home); //---&gt;&gt;&gt;frame.add(panel) ile aynı anlamı taşır
        frame_home.setBounds(700, 200, 400, 700);
        frame_home.setVisible(true);
        frame_home.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame_home.setIconImage(imgicon.getImage());
        frame_home.setResizable(false);
        frame_home.addWindowListener(new WindowAdapter() {
   public void windowClosing(WindowEvent evt) {
       frame_home.dispose();
       try {
           gizle();
       } catch (IOException ex) {
           Logger.getLogger(NamazVakti.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ParseException ex) {
           Logger.getLogger(NamazVakti.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
  });
        

       
        sehirsec.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                sehirsec();
            }
        });
       
        r1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                sehirsec();
            }
        });
        guncelle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String deger="";
                if(parts[0]!=""){
                    deger=parts[0];
                    try {
                        boolean sonuc=getTime(deger);
                        if(sonuc){
                           JOptionPane.showMessageDialog(null, "Güncellendi...","Mesaj",JOptionPane.INFORMATION_MESSAGE); 
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(NamazVakti.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                else {sehirsec();}
               
               
            }
        });
        exit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
           
           if(il.equals(""))
        sehirsec();
            
   }
     public void CloseFrame(){
    frame_home.dispose();
}
     
     public void gizle() throws IOException, ParseException{
       f = new JDialog();//taskbarda icon göstermemek için dialog
        ImageIcon imgicon = new ImageIcon(iconPath);
  f.getContentPane().setBackground(Color.yellow);// arka Plana Renk Veriyoruz..

  
  f.setSize(170, 50);
  f.setUndecorated(true);//Oluşturduğumuz pencerenin kenarlıklarını 
          //ve büyütme küçültme düğmeleriyle kapatmak için yazdık

  f.setOpacity(0.7f);//pencerenin Şeffeflığını 0-1 arasında float olarak değiştirebiliriz.
  String kayitli="Boş";
  
   
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("HH:mm");
        long nowTime=ft.parse(ft.format(dNow)).getTime();
        System.out.println(nowTime);
        long kalansure=0;
        for(int i=2;parts.length>i;i++){
            if(ft.parse(parts[i]).getTime()>nowTime){
                System.out.println(ft.parse(parts[i]).getTime());
                kalansure = ft.parse(parts[4]).getTime()-nowTime;
                break;  
            }
        }
        kayitli = "Vakte kalan: "+("").format("%02d",kalansure/3600000)+":"+("").format("%02d",kalansure/60000);

        goster=new JButton(kayitli);// buton
        goster.setBounds(0, 0, 200, 70);
        goster.setBackground(Color.white);//renk
        goster.setForeground(Color.black);
       
        
       

        
  f.getContentPane().add(goster);
  GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
  GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
            Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
  int x = (int) rect.getMaxX() - f.getWidth();
        int y = (int) rect.getMaxY() - f.getHeight()-50;
  f.setLocation(x, y);//Penceremizin ekranda açılmasını istediğimiz yeri koordinantlarla belirliyoruz..
  f.setDefaultCloseOperation(f.DO_NOTHING_ON_CLOSE);
  f.setAlwaysOnTop (true);
  f.setUndecorated(true);
  f.setIconImage(imgicon.getImage());
  f.setVisible(true);

 
  f.addWindowListener(new WindowAdapter() {
   public void windowClosing(WindowEvent evt) {
       
    f.dispose();
       try {
           anasayfa();
       } catch (FileNotFoundException ex) {
           Logger.getLogger(NamazVakti.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(NamazVakti.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
  });
  goster.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                f.dispose();
                try {
                    anasayfa();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(NamazVakti.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(NamazVakti.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
     }
     
    public String Convert(String kelimecik) 
{ 
kelimecik = kelimecik.replace('ö', 'o'); 
kelimecik = kelimecik.replace('ü', 'u'); 
kelimecik = kelimecik.replace('ğ', 'g'); 
kelimecik = kelimecik.replace('ş', 's'); 
kelimecik = kelimecik.replace('ı', 'i'); 
kelimecik = kelimecik.replace('ç', 'c'); 
kelimecik = kelimecik.replace('Ö', 'O'); 
kelimecik = kelimecik.replace('Ü', 'U'); 
kelimecik = kelimecik.replace('Ğ', 'G'); 
kelimecik = kelimecik.replace('Ş', 'S'); 
kelimecik = kelimecik.replace('İ', 'I'); 
kelimecik = kelimecik.replace('Ç', 'C'); 

return kelimecik; 
}
 
  
    public static void main(String[] args) throws IOException {
       NamazVakti sehirsec=new NamazVakti();
       
       
     Runnable r = new Runnable() {
         public void run() {
           
         }
     };
     ExecutorService executor = Executors.newCachedThreadPool();
     executor.submit(r);
       
      sehirsec.oku();
      sehirsec.anasayfa();
    }
    
   public boolean oku() throws FileNotFoundException, IOException{
       File file = new File("dosya.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
       FileReader fileReader = new FileReader(file);
String line="boş1";

BufferedReader br = new BufferedReader(fileReader);

        
            while ((line = br.readLine()) != null) {
                
                System.out.println("Okunan veri: "+line);
                parts= line.split(" ");//parçalıyoruz
                return true;
            }

        
            br.close();
        return false;
        

   }
    
  
}

    
