package com.example.mylibrary.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.BookDAO;
import com.example.mylibrary.model.Comment;
import com.example.mylibrary.model.CommentDAO;
import com.example.mylibrary.model.GenreOfBook;
import com.example.mylibrary.model.MyLibraryDB;
import com.example.mylibrary.model.User;
import com.example.mylibrary.model.UserDAO;

import java.util.List;

public class MyLibraryRepository {

    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final CommentDAO commentDAO;
    private MutableLiveData<List<User>> userLiveData = new MutableLiveData<>();

    public MyLibraryRepository(Context context) {
        MyLibraryDB myLibraryDB = MyLibraryDB.getDB(context);
        bookDAO = myLibraryDB.getBookDAO();
        userDAO = myLibraryDB.getUserDAO();
        commentDAO = myLibraryDB.getCommentDAO();
    }


    public LiveData<List<Book>> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public LiveData<List<Book>> getDeferredBooks() {
        return bookDAO.getDeferredBooks();
    }

    public LiveData<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public LiveData<List<Book>> getAddedInLibraryBooks() {
        return bookDAO.getAddedInLibraryBooks();
    }

    public LiveData<List<Book>> getSearchResultByBookName(String query, boolean isFreeOnly) {
        if (isFreeOnly) {
            return bookDAO.getSearchResultByBookNameFreeOnly(query);
        } else {
            return bookDAO.getSearchResultByBookName(query);
        }

    }

    public LiveData<List<Book>> getSearchResultByAuthor(String query, boolean isFreeOnly) {
        if (isFreeOnly) {
            return bookDAO.getSearchResultByAuthorFreeOnly(query);
        } else {
            return bookDAO.getSearchResultByAuthor(query);
        }

    }

    public LiveData<List<Book>> getSearchResultByGenre(String query, boolean isFreeOnly) {
        if (isFreeOnly) {
            return bookDAO.getSearchResultByGenreFreeOnly(GenreOfBook.DETECTIVE.findGenreByGenreName(query));
        } else {
            return bookDAO.getSearchResultByGenre(GenreOfBook.DETECTIVE.findGenreByGenreName(query));
        }

    }

    public void insertBooks(List<Book> books) {
        bookDAO.insertBooks(books);
    }

    public void updateBook(Book book) {
        bookDAO.updateBook(book);
    }

    public LiveData<List<Comment>> getCommentsByBook(int bookId){
        return commentDAO.getCommentsByBook(bookId);
    }

    public LiveData<List<User>> getUser(String email, String password) {
        return userDAO.getUser(email, password);

    }

    public LiveData<User> getUserById(int userId){
        return userDAO.getUserById(userId);
    }

    public LiveData<Book> getBookById(int bookId){
        return bookDAO.getBookById(bookId);
    }

    public void insertUser(User newUser) {
        userDAO.insertUser(newUser);
    }

    public void insertComments(List<Comment> comments) {
        commentDAO.insertComments(comments);
    }

    public void insertComment(Comment comment){
        commentDAO.insertComment(comment);
    }

    public void insertUsers(List<User> users) {
        userDAO.insertUsers(users);
    }

//    public void fillDB() {
//        List<Book> booksList = new ArrayList<>();
//
//        booksList.add(new Book("Борис Акунин", "Азазель", R.raw.azazel_text, GenreOfBook.DETECTIVE, "Двадцатилетний коллежский регистратор сыскной полиции Эраст Петрович Фандорин с разрешения начальства начинает расследовать подозрительное самоубийство студента из «золотой молодежи» и выходит на запутанный глобальный заговор, который определит всю его жизнь... Кто же стоит за этим заговором?", "Издательство «Захаров»", "Бесплатно", R.drawable.azazel_cover));
//        booksList.add(new Book("Аркадий Адамов", "Злым ветром", R.raw.evil_wind_text, GenreOfBook.DETECTIVE, "Герою этого романа, инспектору уголовного розыска Виталию Лосеву, поручено расследование кражи в гостинице. Однако не очень сложное на первый взгляд дело перерастает в криминальный беспредел, в центре которого – жестокое убийство.", "Издательство «Молодая гвардия»", "Бесплатно", R.drawable.evil_wind_cover));
//        booksList.add(new Book("Альбина Нури", "Пятый неспящий", R.raw.fifth_sleepless_text, GenreOfBook.DETECTIVE, "Отношения Розы с родными всегда оставляли желать лучшего: мать постоянно читала нотации, теткино высокомерное покровительство раздражало, деду не было до нее дела. Она ни за что не осталась бы с ними под одной крышей, но из-за зимней непогоды дороги замело, и девушка оказалась заперта с семьей в загородном особняке тетки. После новогодней ночи Роза поняла, что в доме творится нечто странное и страшное… Его обитатели совсем перестали спать, обнаружили, что в поселке, кроме них, никого не осталось, а полнолуние необъяснимо затянулось. Все их попытки выбраться в город провалились, а за окнами каждую ночь стала появляться фигура в черном капюшоне…", "Издательство «Эксмо»", "250р.", R.drawable.fifth_sleepless_cover));
//        booksList.add(new Book("Валерия Вербинина", "Отравленная маска", R.raw.poisoned_mask_text, GenreOfBook.DETECTIVE, "Семейство Амалии разорено! Вот если бы она удачно вышла замуж... Девушка не сомневалась, что будет иметь успех в свете и вовсю готовилась к балу у Ланиных. Но блеснуть ей так и не удалось: бал отменили из-за трагических обстоятельств. Погибла Жюли Ланина. В доме ее родителей Амалия познакомилась с чиновником департамента полиции, милым неуклюжим Сашей Зябликовым. Под большим секретом он поведал, что уже несколько богатых знатных девушек умерли без всяких на то причин. Амалия заинтересовалась его расследованием, и с ней начали происходить странные вещи: сначала ее чуть не сбила карета, потом погиб котенок, выпивший молоко из ее чашки…", "Издательство «Эксмо»", "Бесплатно", R.drawable.poisoned_mask_cover));
//        booksList.add(new Book("Наталья Тимошенко", "Тишина старого кладбища", R.raw.silence_of_the_old_cemetry_text, GenreOfBook.DETECTIVE, "На старом питерском кладбище исчезают девушки. Полиция никак не может выйти на след, подозревая работу серийного маньяка. Но однажды обычная, ничем до того не примечательная студентка, вдруг получает электронные письма от своей сокурсницы, одной из числа пропавших.", "Издательство «Эксмо»", "475р.", R.drawable.silence_of_the_old_cemetry_cover));
//        booksList.add(new Book("Фёдор Достоевский", "Преступление и наказание", R.raw.crime_and_punishment_text, GenreOfBook.NOVEL, "Родион Раскольников — стеснённый в средствах студент. Он ютится в крохотной комнате и размышляет о справедливости. Ради неё он осмысленно совершает преступление и получает наказание: страшась расплаты, испытывая муки совести, невольно стремясь к раскаянию.", "Издательство «АСТ»", "Бесплатно", R.drawable.crime_and_punishment_cover));
//        booksList.add(new Book("Николай Гоголь", "Мертвые души", R.raw.dead_souls_text, GenreOfBook.NOVEL, "Как и 160 лет назад, поэма заставляет читателя с головой погрузиться в атмосферу гоголевской России; она вызывает массу размышлений не только о прошлом, но также о настоящем и будущем нашей страны. По-прежнему актуален вопрос автора: \"Русь, куда несешься ты, дай ответ?\"", "Издательство «Азбука»", "Бесплатно", R.drawable.dead_souls_cover));
//        booksList.add(new Book("Александр Пушкин", "Капитанская дочка", R.raw.capitan_daughter_text, GenreOfBook.NOVEL, "На склоне лет помещик Пётр Андреевич Гринёв ведёт повествование о бурных событиях своей молодости. Детство своё он провёл в родительском поместье в Симбирской губернии, пока в 16 лет строгий отец — офицер в отставке — не распорядился отправить его служить в армию. Волею судьбы по пути к месту службы молодой офицер встречается с Емельяном Пугачёвым, который тогда был просто беглым, никому не известным казаком. Во время бурана тот соглашается проводить Гринёва с его старым слугой Савельичем к постоялому двору. В знак признательности за услугу Пётр отдаёт ему свой заячий тулуп. Приехав на службу в пограничную Белогорскую крепость, Пётр влюбляется в дочь коменданта крепости, Машу Миронову. Сослуживец Гринёва, офицер Алексей Швабрин, с которым он познакомился уже в крепости, тоже оказывается неравнодушен к капитанской дочери и вызывает Петра на дуэль, в ходе...", "Издательство «Азбука»", "Бесплатно", R.drawable.capitan_daughter_cover));
//        booksList.add(new Book("Иван Тургенев", "Отцы и дети", R.raw.father_and_sons_text, GenreOfBook.NOVEL, "Особенностью общественной жизни 60-х годов XIX века являлась идеологическая борьба двух поколений. Евгений Базаров — радикальный разночинец, характер которого формировался в обстановке великих реформ, разрушающих уклад дворянско-крестьянской России. Он не признает сословий, презирает дворянскую культуру, ратует за упорный труд и ненавидит обломовщину. Его антагонист — Павел Петрович Кирсанов, бывший офицер, полностью опустошенный романом с одной из светстких львиц. Между ними назревает конфликт — конфликт между молодостью, жаждущей перемен, и консерватизмом зрелого возраста...", "Издательство «АСТ»", "Бесплатно", R.drawable.father_and_sons_cover));
//        booksList.add(new Book("Иван Гончаров", "Обломов", R.raw.oblomov_text, GenreOfBook.NOVEL, "Илья Ильич Обломов, молодой дворянин, ведёт праздную жизнь в Петербурге. Его поместье понемногу приходит в упадок, управляющий обворовывает, лучший и единственный друг Штольц упорно добивается успеха. Обломов беспокоится о своей судьбе, но не находит в себе сил переменить сложившуюся ситуацию.", "Издательство «АСТ»", "Бесплатно", R.drawable.oblomov_cover));
//        booksList.add(new Book("Сергей Лукьяненко", "Черновик", R.raw.draft_text, GenreOfBook.FANTASTIC, "Однажды вы приходите домой и обнаруживаете, что в вашей квартире живет другой человек, ваша собака вас не узнает. Потом вас начинают забывать знакомые и родственники и пропадают ваши документы. Что происходит?", "Издательство «АСТ»", "350р.", R.drawable.draft_cover));
//        booksList.add(new Book("Сергей Лукьяненко", "Лабиринт отражений", R.raw.labyrinth_of_reflections_text, GenreOfBook.FANTASTIC, "Мир виртуальной реальности. Мир глубины. Компьютерные корпорации создают в этом мире блистательный город развлечений Диптаун - дорога в него не заказана никому. В глубину уходят в поисках свободы, обретя которую - или ее видимость - пытаются остаться там навсегда. \"Отказников\" выводят с глубины профессиональные спасатели-дайверы, для которых не существует никаких моральных запретов: виртуальные дуэли и компьютерный секс, свои обычаи и законы - порою забавные, а зачастую - жестокие…", "Издательство «АСТ»", "550р.", R.drawable.labyrinth_of_reflections_cover));
//        booksList.add(new Book("Александр Беляев", "Ариэль", R.raw.arielle_text, GenreOfBook.FANTASTIC, "Неподалеку от Мадраса, где расположилась таинственная школа Дандарат, великий, но не признанный ученый доктор Хайд изобретает средство, при помощи которого человек может летать без всякого технического устройства.", "Издательство «Престиж Бук»", "350р.", R.drawable.arielle_cover));
//        booksList.add(new Book("Борис Стругацкий", "Обитаемый остров", R.raw.inhabited_island_text, GenreOfBook.FANTASTIC, "Тысячи людей - в одиночку, семьями, компаниями - устремляются к звездам… Молодой волонтер ГСП Максим Каммерер терпит крушение на планете, пережившей ядерную войну, и оказывается втянутым в стремительно развивающиеся события, которые кардинально меняют его судьбу.", "Издательство «АСТ»", "450р.", R.drawable.inhabited_island_cover));
//        booksList.add(new Book("Дмитрий Глуховский", "Метро 2033", R.raw.metro_2033_text, GenreOfBook.FANTASTIC, "2033 год. Весь мир лежит в руинах. Человечество почти полностью уничтожено. Москва превратилась в город‑призрак, отравленный радиацией и населённый чудовищами. Немногие выжившие люди прячутся в московском метро — самом большом противоатомном бомбоубежище на земле. Его станции превратились в города‑государства, а в туннелях царит тьма и обитает ужас. Артему, жителю ВДНХ, предстоит пройти через все метро, чтобы спасти от страшной опасности свою станцию, а может быть, и всё человечество. Культовый сетевой роман Дмитрия Глуховского уже известен ста тысячам интернет‑пользователей. Выхода этой книги ждали все.", "Издательство «Эксмо»", "899р.", R.drawable.metro_2033_cover));
//        booksList.add(new Book("Александр Дюма", "Граф Монте-Кристо", R.raw.count_of_monte_cristo_text, GenreOfBook.FOREIGN_LITERATURE, "Донос завистника — и невинный Эдмон оказывается за решёткой на долгих четырнадцать лет... Пройдя через каменные мешки замка Иф, закалив волю, разбогатев на пиратских кладах, герой под псевдонимом «Граф Монте-Кристо» возвращается во Францию, чтобы отомстить предателям, сломавшем ему жизнь.", "Издательство «Азбука»", "Бесплатно", R.drawable.count_of_monte_cristo_cover));
//        booksList.add(new Book("Эрих Мария Ремарк", "Ночь в Лиссабоне", R.raw.overnight_in_lisbon_text, GenreOfBook.FOREIGN_LITERATURE, "Это настоящая исповедь отважного, смелого человека, на долю которого выпали немыслимые по тяжести испытания. Это история целого поколения людей, столкнувшихся с войной, попавших в тиски фашизма. Это еще и история любви, перед которой отступает даже смерть.", "Издательство «АСТ»", "560р.", R.drawable.overnight_in_lisbon_cover));
//        booksList.add(new Book("Вальтер Скотт", "Айвенго", R.raw.ivanhoe_text, GenreOfBook.FOREIGN_LITERATURE, "Герой романа, молодой рыцарь Уилфред Айвенго, копьем и мечом защищает свою честь и права, свою возлюбленную, леди Ровену, руки которой всеми средствами домогается жестокий и суровый крестоносец Бриан де Буагильбер. В судьбе юноши горячее участие принимают тайно возвратившийся в Англию король Ричард Львиное Сердце, которого коварные враги пытаются лишить трона, и легендарный разбойник, защитник угнетенных Робин Гуд.", "Издательство «АСТ»", "450р.", R.drawable.ivanhoe_cover));
//        booksList.add(new Book("Рэй Брэдбери", "Вино из одуванчиков", R.raw.dandelion_wine_text, GenreOfBook.FOREIGN_LITERATURE, "Семейство Сполдингов, проживающее в маленьком американском городке, бережно хранит свои традиции. Одна из них – приготовление вина из одуванчиков, «пойманного и закупоренного в бутылки лета». А двенадцатилетний Дуглас Сполдинг решает сохранить память о летних днях по-своему: он ведет дневник, фиксируя в нем не только «обряды и обыкновения», но и собственные «открытия и откровения». Очень богатым на них оказывается это лето – сотканное из множества важных событий, обретений и потерь. Яркое, удивительное, фантастическое лето 1928 года…", "Издательство «Эксмо»", "720р.", R.drawable.dandelion_wine_cover));
//        booksList.add(new Book("Стендаль", "Красное и чёрное", R.raw.red_and_black_text, GenreOfBook.FOREIGN_LITERATURE, "Франция после Июльской революции 1830 года. В провинциальном городке Верьер молодой человек Жюльен Сорель выделяется своим умом и честолюбием. С детства он мечтает о военной славе Наполеона, его также привлекает и церковная карьера.", "Издательство «Азбука»", "445р.", R.drawable.red_and_black_cover));
//        booksList.add(new Book("Максим Горький", "Старуха Изергиль", R.raw.old_woman_izergil_text, GenreOfBook.STORY, "Герой рассказа беседует со старухой-цыганкой, которая поведала ему три истории: о человеке, наказанном за гордость, о горящем сердце Данко и — о себе самой...", "Издательство «Феникс»", "Бесплатно", R.drawable.old_woman_izergil_cover));
//        booksList.add(new Book("Иван Бунин", "Господин из Сан-Франциско", R.raw.gentleman_from_san_francisco_text, GenreOfBook.STORY, "В свои пятьдесят восемь лет он заслужил отдых и удовольствия. До того работал не покладая рук, и наконец достиг желаемого. И теперь он едет в двухгодичное путешествие по миру вместе с женой и дочерью…", "Издательство «Эксмо»", "Бесплатно", R.drawable.gentleman_from_san_francisco_cover));
//        booksList.add(new Book("Антон Чехов", "Каштанка", R.raw.kashtanka_text, GenreOfBook.STORY, "Маленькая рыжая собачка Каштанка, похожая мордочкой на лисичку, жила не очень хорошей жизнью. Однажды она даже попала к другому хозяину, сменила имя на Тётку и готовилась выступать в цирке. Но вновь услышав родной голос, не сомневаясь ни секунды, вернулась к прежнему хозяину.", "Издательство «Феникс»", "Бесплатно", R.drawable.kashtanka_cover));
//        booksList.add(new Book("Владимир Короленко", "Дети подземелья", R.raw.children_of_the_dungeon_text, GenreOfBook.STORY, "События разворачиваются в небольшом польском местечке Княж-городке. Вася и Соня — дети уважаемого в городе человека, судьи. Их жизнь внешне спокойна и размерена. Только семейная трагедия, смерть матери, когда мальчику было шесть лет, подтачивает благополучие. Отец все более удаляется от сына, и всю свою любовь сосредотачивает на дочери Соне. Мальчик чувствует себя брошенным, бесцельно слоняется по городу, пока однажды не встречает на своем пути двух нищих бродяжек, маленьких Валека и Марусю, вынужденных попрошайничать и подворовывать, чтобы выжить.", "Издательство «Стрекоза»", "Бесплатно", R.drawable.children_of_the_dungeon_cover));
//        booksList.add(new Book("Антон Чехов", "Хамелеон", R.raw.chameleon_text, GenreOfBook.STORY, "Однажды на базарной площади случилось невообразимое происшествие: собака укусила пьяного золотых дел мастера Хрюкина. Полицейский надзиратель Очумелов тут же быстро собирается навести порядок и прекратить безобразие, а собаку истребить. Хотя, а ну как собака не простая дворняга, а генерала Жигалова?", "Издательство «Белый город»", "Бесплатно", R.drawable.chameleon_cover));
//
//        insertBooks(booksList);
//
//        List<User> userList = new ArrayList<>();
//
//        userList.add(new User("NastyaKulikova@mail.ru", "1234567", "Настя"));
//        userList.add(new User("DarinaKulikova@gmail.com", "123qweasd", "Дарина"));
//        userList.add(new User("AlenaIsay@mail.ru", "star1122", "Алена"));
//        insertUsers(userList);
//
//        List<Comment> commentList = new ArrayList<>();
//
//        commentList.add(new Comment( 1,8,"Книга очень поучительна! Советую всем к прочтению"));
//        commentList.add(new Comment(3,14,"Ничего не понятно!!!"));
//        commentList.add(new Comment(1,7,"Любимая книга еще со школы"));
//        commentList.add(new Comment(2,7,"А мне не очень понравилась..."));
//        commentList.add(new Comment(3,22,"Очень грустная история про милую собачку. Чувствительным советую не читать"));
//
//        insertComment(commentList);
//
//
//    }


}
