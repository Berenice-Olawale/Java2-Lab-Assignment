# Documentation draft – tasks 1-7 (authentication, theme, Mainpage, Database, Contact, Messages)

> Draft text for the PDF documentation. Fill in the names/Neptun codes, add screenshots
> (suggestions are marked 📸) and merge it with your teammate's part.

## How to run locally

1. Start XAMPP (Apache + MySQL) and run `database/seed.sql` in phpMyAdmin (creates `java_seminar` with the Notebook tables and data).
2. Open the project in IntelliJ (JDK 25, as in `pom.xml`) and run `DemoApplication`, or `mvn spring-boot:run`.
3. Open <http://localhost:8080/>. Default admin: user `admin`, password `Admin123!`
   (`app.admin.*` in `application.properties`).

DB connection settings are read from environment variables (`MYSQL_HOST`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`);
the defaults are the local XAMPP values. On the Linux server set DB_NAME/DB_USER/DB_PASSWORD to the Neptun code.

## Task 1 – Starting point
The project is built on the *AUTHENTICATION lesson solution* (Solutions.zip → `08-09-Spring-Boot-Security-Authentication`):
`User` entity, `UserRepository`, `CustomUserDetailsService`, `WebSecurityConfig`, BCrypt password hashing.
Changes made while developing it further: new Bootstrap templates, validated registration form, role rules for the new menu items,
default admin account created at start-up, the old `/user` and `/admin` demo pages removed, redirects made safe for a WAR deployed under a context path.

## Task 2 – Free responsive theme
**Theme: "Flatly" from [Bootswatch](https://bootswatch.com/flatly/)** – a free, responsive Bootstrap 5 theme (MIT licence).
It is loaded from the jsDelivr CDN in `templates/fragments.html` (fragment `head`); small additions are in `static/css/site.css`.
The responsive collapsing menu (burger button on phones) is the Bootstrap navbar in fragment `navbar`.
**Names and Neptun codes** are in the common footer (fragment `footer`), filled from `app.creators` in `application.properties`.
📸 Screenshot of a page on desktop and on a phone-sized window.

## Task 3 – Authentication
| Role | Who | Menu items |
|---|---|---|
| visitor | not logged in | Mainpage, Database, Contact, Chart, REST, Login, Registration |
| registered visitor | `ROLE_USER` | + Messages, Logout |
| admin | `ROLE_ADMIN` | + Messages, CRUD, Logout |

* Registration: `AuthController` (`GET/POST /register`), `RegisterForm` (validation), password stored with BCrypt, role `USER`.
* Login/Logout: Spring Security form login (`/login`), logout is a POST form in the menu.
* Rules: `config/WebSecurityConfig.java` (`/messages` → USER/ADMIN, `/crud` → ADMIN, public pages `permitAll`).
* The menu shows items depending on the role with `sec:authorize` in `fragments.html`.
* Admin account: created by `config/AdminInitializer.java` at start-up.
📸 Menu as visitor / user / admin; registration with an error; login error.

## Task 4 – Mainpage
`HomeController` + `templates/home.html`: gradient hero section, feature cards linking to every menu item, role overview.
📸 Whole front page.

## Task 5 – Database menu
Chosen database: **Notebook** (ReNew Ltd., refurbished notebooks) – tables `notebook` (246 rows), `processor` (44) and `opsystem` (12).
`DatabaseController` → `templates/database.html`. Entities are in `entity/` (`Notebook`, `Processor`, `OpSystem`) and the Spring Data
repositories in `repository/` (`NotebookRepository`, `ProcessorRepository`, `OpSystemRepository`).
`Notebook` has `@ManyToOne` relations to `Processor` and `OpSystem`, so one table shows each notebook with its processor and operating system.
The notebook list can be filtered by manufacturer (case-insensitive, because the data contains both "ASUS" and "Asus").
The processor and operating system tables are listed below it.
📸 Database page (unfiltered and filtered).

## Task 6 – Contact menu
`ContactController` (`GET/POST /contact`), form object `dto/ContactForm.java` with server-side constraints
(`@NotBlank`, `@Size`, `@Email`) checked with `@Valid` + `BindingResult`; errors are shown next to the fields.
A valid message is saved with `ContactMessageRepository` into the `contact_messages` table (with `sent_at` = current time),
then the browser is redirected (Post/Redirect/Get). Unit test: `ContactFormValidationTest`.
📸 Form with validation errors; success message; the row in phpMyAdmin.

## Task 7 – Messages menu
`MessagesController` → `templates/messages.html`. `ContactMessageRepository.findAllByOrderBySentAtDesc()` returns the newest message first;
each message shows its sending time. The URL is protected in `WebSecurityConfig`, the menu item only appears for logged-in users.
📸 Messages page; trying `/messages` while logged out (redirect to login).

## Who did what
Tasks 1-7 above: _NAME 1 (NEPTUN1)_. Tasks 8-10 (Chart, CRUD, REST) : _NAME 2 (NEPTUN2)_.
