package api;

import com.uwetrottmann.tmdb2.entities.*;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ApplicationScoped // singleton
@Path("/Mock_search")
public class ResearchRestServiceMock {

    @GET
    @Path("/company/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchCompany(@PathParam("query") String query) {

        CompanyResultsPage companyResultsPage = buildCompanyResultsPage(
                Arrays.asList(
                        buildBaseCompany(72095, "W Productions"),
                        buildBaseCompany(137967, "W. Pilate"),
                        buildBaseCompany(145254, "W&S")
                )
        );

        return Response.ok(companyResultsPage).build();
    }

    @GET
    @Path("/company/{query}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchCompanyPage(@PathParam("query") String query, @PathParam("page") Integer page) {

        CompanyResultsPage companyResultsPage = buildCompanyResultsPage(
                Arrays.asList(
                        buildBaseCompany(72095, "W Productions"),
                        buildBaseCompany(137967, "W. Pilate"),
                        buildBaseCompany(145254, "W&S")
                )
        );

        return Response.ok(companyResultsPage).build();
    }

    @GET
    @Path("/collection/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchCollection(@PathParam("query") String query) {

        CollectionResultsPage collectionResultsPage = buildCollectionResultsPage(
                Arrays.asList(
                        buildBaseCollection(89188, "/zOeny02J2PgaZLSydAUDfHPVRVu.jpg", "Harry Palmer - Saga", "/33KuSJWp0qOkBvDAB0LNh9OhUbY.jpg"),
                        buildBaseCollection(1241, "/wfnMt6LGqYHcNyOfsuusw5lX3bL.jpg", "Harry Potter - Saga", "/er385HwV4lPgFPzck3IO3MZztnh.jpg"),
                        buildBaseCollection(10456, "/nlpixhOhb3JmFqVXKjCwpYFpldf.jpg", "L'Inspecteur Harry - Saga", "/q88lU9rGKE87Jk7R4dg7Mgrx0lE.jpg")
                )
        );

        return Response.ok(collectionResultsPage).build();
    }

    @GET
    @Path("/collection/{query}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchCollectionPage(@PathParam("query") String query, @PathParam("page") Integer page) {

        CollectionResultsPage collectionResultsPage = buildCollectionResultsPage(
                Arrays.asList(
                        buildBaseCollection(89188, "/zOeny02J2PgaZLSydAUDfHPVRVu.jpg", "Harry Palmer - Saga", "/33KuSJWp0qOkBvDAB0LNh9OhUbY.jpg"),
                        buildBaseCollection(1241, "/wfnMt6LGqYHcNyOfsuusw5lX3bL.jpg", "Harry Potter - Saga", "/er385HwV4lPgFPzck3IO3MZztnh.jpg"),
                        buildBaseCollection(10456, "/nlpixhOhb3JmFqVXKjCwpYFpldf.jpg", "L'Inspecteur Harry - Saga", "/q88lU9rGKE87Jk7R4dg7Mgrx0lE.jpg")
                )
        );

        return Response.ok(collectionResultsPage).build();
    }

    @GET
    @Path("/keyword/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchKeyword(@PathParam("query") String query) {

        KeywordResultsPage keywordResultsPage = buildKeywordResultsPage(
                Arrays.asList(
                        buildBaseKeyword(2964, "future"),
                        buildBaseKeyword(9951, "alien"),
                        buildBaseKeyword(12988, "pirate"),
                        buildBaseKeyword(208879, "mockbuster")
                )
        );

        return Response.ok(keywordResultsPage).build();
    }

    @GET
    @Path("/keyword/{query}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchKeywordPage(@PathParam("query") String query, @PathParam("page") Integer page) {

        KeywordResultsPage keywordResultsPage = buildKeywordResultsPage(
                Arrays.asList(
                        buildBaseKeyword(2964, "future"),
                        buildBaseKeyword(9951, "alien"),
                        buildBaseKeyword(12988, "pirate"),
                        buildBaseKeyword(208879, "mockbuster")
                )
        );

        return Response.ok(keywordResultsPage).build();
    }

    @GET
    @Path("/multi/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchMedia(@PathParam("query") String query) {

        MediaResultsPage mediaResultsPage = buildMediaResultsPage(
                Arrays.asList(
                        buildMedia(
                                null,
                                buildBasePerson("/wo2hJpn04vbtmh0B9utCFdsQhxM.jpg", 6193, "Leonardo DiCaprio", 16.849, Arrays.asList(
                                        buildMedia(
                                                buildBaseMovie(27205, "/s3TBrRGB1iav7gFOCNx3H31MoES.jpg", Arrays.asList(28, 878, 12), "Inception", "Dom Cobb est un voleur expérimenté, le meilleur dans l'art dangereux de l'extraction, voler les secrets les plus intimes enfouis au plus profond du subconscient durant une phase de rêve, lorsque l'esprit est le plus vulnérable. Les capacités de Cobb ont fait des envieux dans le monde tourmenté de l'espionnage industriel alors qu'il devient fugitif en perdant tout ce qu'il a un jour aimé. Une chance de se racheter lui est alors offerte. Une ultime mission grâce à laquelle il pourrait retrouver sa vie passée mais uniquement s'il parvient à accomplir l'impossible inception.", null, "/aej3LRUga5rhgkmRP6XMFw3ejbl.jpg", new Date(1279152000000L), "Inception", 8.3, 28982),
                                                null,
                                                com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                                        ),
                                        buildMedia(
                                                buildBaseMovie(68718, "/lVcI3MOtumvEbOdS1Og7QoV6Lfc.jpg", Arrays.asList(18, 37), "Django Unchained", "Dans le sud des États‐Unis, deux ans avant la guerre de Sécession, le Dr King Schultz, un chasseur de primes allemand, fait l’acquisition de Django, un esclave qui peut l’aider à traquer les frères Brittle, les meurtriers qu’il recherche. Schultz promet à Django de lui rendre sa liberté lorsqu’il aura capturé les Brittle – morts ou vifs. Alors que les deux hommes pistent les dangereux criminels, Django n’oublie pas que son seul but est de retrouver Broomhilda, sa femme, dont il fut séparé à cause du commerce des esclaves… Lorsque Django et Schultz arrivent dans l’immense plantation du puissant Calvin Candie, ils éveillent les soupçons de Stephen, un esclave qui sert Candie et a toute sa confiance. Le moindre de leurs mouvements est désormais épié par une dangereuse organisation de plus en plus proche… Si Django et Schultz veulent espérer s’enfuir avec Broomhilda, ils vont devoir choisir entre l’indépendance et la solidarité, entre le sacrifice et la survie…", null, "/hodO0759IB5LbzUiiLKB50gT2UO.jpg", new Date(1356393600000L), "Django Unchained", 	8.1, 20551),
                                                null,
                                                com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                                        ))
                                ),
                                com.uwetrottmann.tmdb2.enumerations.MediaType.PERSON
                        ),
                        buildMedia(
                                buildBaseMovie(792166, "/3rw5aaSAzE64mdkeOk6N3KPlSWJ.jpg", Collections.singletonList(99), "Leonardo DiCaprio: Most Wanted!", "De « Titanic » de James Cameron, qui a déclenché une « Leomania » planétaire, aux rôles plus sombres de la maturité et à sa fidèle collaboration avec Martin Scorsese, le portrait documenté et touchant d’un acteur virtuose.", 3.946, "/wniUs5xO5uJqHFF97jNG8rQnlfA.jpg", new Date(1611878400000L), "Leonardo DiCaprio: Most Wanted!", 7.9, 6),
                                null,
                                com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                        )

                )
        );

        return Response.ok(mediaResultsPage).build();
    }

    @GET
    @Path("/multi/{query}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchMediaPage(@PathParam("query") String query, @PathParam("page") Integer page) {

        MediaResultsPage mediaResultsPage = buildMediaResultsPage(
                Arrays.asList(
                        buildMedia(
                                null,
                                buildBasePerson("/wo2hJpn04vbtmh0B9utCFdsQhxM.jpg", 6193, "Leonardo DiCaprio", 16.849, Arrays.asList(
                                        buildMedia(
                                                buildBaseMovie(27205, "/s3TBrRGB1iav7gFOCNx3H31MoES.jpg", Arrays.asList(28, 878, 12), "Inception", "Dom Cobb est un voleur expérimenté, le meilleur dans l'art dangereux de l'extraction, voler les secrets les plus intimes enfouis au plus profond du subconscient durant une phase de rêve, lorsque l'esprit est le plus vulnérable. Les capacités de Cobb ont fait des envieux dans le monde tourmenté de l'espionnage industriel alors qu'il devient fugitif en perdant tout ce qu'il a un jour aimé. Une chance de se racheter lui est alors offerte. Une ultime mission grâce à laquelle il pourrait retrouver sa vie passée mais uniquement s'il parvient à accomplir l'impossible inception.", null, "/aej3LRUga5rhgkmRP6XMFw3ejbl.jpg", new Date(1279152000000L), "Inception", 8.3, 28982),
                                                null,
                                                com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                                        ),
                                        buildMedia(
                                                buildBaseMovie(68718, "/lVcI3MOtumvEbOdS1Og7QoV6Lfc.jpg", Arrays.asList(18, 37), "Django Unchained", "Dans le sud des États‐Unis, deux ans avant la guerre de Sécession, le Dr King Schultz, un chasseur de primes allemand, fait l’acquisition de Django, un esclave qui peut l’aider à traquer les frères Brittle, les meurtriers qu’il recherche. Schultz promet à Django de lui rendre sa liberté lorsqu’il aura capturé les Brittle – morts ou vifs. Alors que les deux hommes pistent les dangereux criminels, Django n’oublie pas que son seul but est de retrouver Broomhilda, sa femme, dont il fut séparé à cause du commerce des esclaves… Lorsque Django et Schultz arrivent dans l’immense plantation du puissant Calvin Candie, ils éveillent les soupçons de Stephen, un esclave qui sert Candie et a toute sa confiance. Le moindre de leurs mouvements est désormais épié par une dangereuse organisation de plus en plus proche… Si Django et Schultz veulent espérer s’enfuir avec Broomhilda, ils vont devoir choisir entre l’indépendance et la solidarité, entre le sacrifice et la survie…", null, "/hodO0759IB5LbzUiiLKB50gT2UO.jpg", new Date(1356393600000L), "Django Unchained", 	8.1, 20551),
                                                null,
                                                com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                                        ))
                                ),
                                com.uwetrottmann.tmdb2.enumerations.MediaType.PERSON
                        ),
                        buildMedia(
                                buildBaseMovie(792166, "/3rw5aaSAzE64mdkeOk6N3KPlSWJ.jpg", Collections.singletonList(99), "Leonardo DiCaprio: Most Wanted!", "De « Titanic » de James Cameron, qui a déclenché une « Leomania » planétaire, aux rôles plus sombres de la maturité et à sa fidèle collaboration avec Martin Scorsese, le portrait documenté et touchant d’un acteur virtuose.", 3.946, "/wniUs5xO5uJqHFF97jNG8rQnlfA.jpg", new Date(1611878400000L), "Leonardo DiCaprio: Most Wanted!", 7.9, 6),
                                null,
                                com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                        )

                )
        );

        return Response.ok(mediaResultsPage).build();
    }

    @GET
    @Path("/movie/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchMovie(@PathParam("query") String query, @QueryParam("year") Integer year, @QueryParam("primaryReleaseYear") Integer primaryReleaseYear) {

        MovieResultsPage movieResultsPage = buildMovieResultsPage(
                Arrays.asList(
                        buildBaseMovie(27205, "/s3TBrRGB1iav7gFOCNx3H31MoES.jpg", Arrays.asList(28, 878, 12), "Inception", "Dom Cobb est un voleur expérimenté, le meilleur dans l'art dangereux de l'extraction, voler les secrets les plus intimes enfouis au plus profond du subconscient durant une phase de rêve, lorsque l'esprit est le plus vulnérable. Les capacités de Cobb ont fait des envieux dans le monde tourmenté de l'espionnage industriel alors qu'il devient fugitif en perdant tout ce qu'il a un jour aimé. Une chance de se racheter lui est alors offerte. Une ultime mission grâce à laquelle il pourrait retrouver sa vie passée mais uniquement s'il parvient à accomplir l'impossible inception.", null, "/aej3LRUga5rhgkmRP6XMFw3ejbl.jpg", new Date(1279152000000L), "Inception", 8.3, 28982),
                        buildBaseMovie(68718, "/lVcI3MOtumvEbOdS1Og7QoV6Lfc.jpg", Arrays.asList(18, 37), "Django Unchained", "Dans le sud des États‐Unis, deux ans avant la guerre de Sécession, le Dr King Schultz, un chasseur de primes allemand, fait l’acquisition de Django, un esclave qui peut l’aider à traquer les frères Brittle, les meurtriers qu’il recherche. Schultz promet à Django de lui rendre sa liberté lorsqu’il aura capturé les Brittle – morts ou vifs. Alors que les deux hommes pistent les dangereux criminels, Django n’oublie pas que son seul but est de retrouver Broomhilda, sa femme, dont il fut séparé à cause du commerce des esclaves… Lorsque Django et Schultz arrivent dans l’immense plantation du puissant Calvin Candie, ils éveillent les soupçons de Stephen, un esclave qui sert Candie et a toute sa confiance. Le moindre de leurs mouvements est désormais épié par une dangereuse organisation de plus en plus proche… Si Django et Schultz veulent espérer s’enfuir avec Broomhilda, ils vont devoir choisir entre l’indépendance et la solidarité, entre le sacrifice et la survie…", null, "/hodO0759IB5LbzUiiLKB50gT2UO.jpg", new Date(1356393600000L), "Django Unchained", 	8.1, 20551)
                )
        );

        return Response.ok(movieResultsPage).build();
    }

    @GET
    @Path("/movie/{query}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchMoviePage(@PathParam("query") String query, @PathParam("page") Integer page, @QueryParam("year") Integer year, @QueryParam("primaryReleaseYear") Integer primaryReleaseYear) {

        MovieResultsPage movieResultsPage = buildMovieResultsPage(
                Arrays.asList(
                        buildBaseMovie(27205, "/s3TBrRGB1iav7gFOCNx3H31MoES.jpg", Arrays.asList(28, 878, 12), "Inception", "Dom Cobb est un voleur expérimenté, le meilleur dans l'art dangereux de l'extraction, voler les secrets les plus intimes enfouis au plus profond du subconscient durant une phase de rêve, lorsque l'esprit est le plus vulnérable. Les capacités de Cobb ont fait des envieux dans le monde tourmenté de l'espionnage industriel alors qu'il devient fugitif en perdant tout ce qu'il a un jour aimé. Une chance de se racheter lui est alors offerte. Une ultime mission grâce à laquelle il pourrait retrouver sa vie passée mais uniquement s'il parvient à accomplir l'impossible inception.", null, "/aej3LRUga5rhgkmRP6XMFw3ejbl.jpg", new Date(1279152000000L), "Inception", 8.3, 28982),
                        buildBaseMovie(68718, "/lVcI3MOtumvEbOdS1Og7QoV6Lfc.jpg", Arrays.asList(18, 37), "Django Unchained", "Dans le sud des États‐Unis, deux ans avant la guerre de Sécession, le Dr King Schultz, un chasseur de primes allemand, fait l’acquisition de Django, un esclave qui peut l’aider à traquer les frères Brittle, les meurtriers qu’il recherche. Schultz promet à Django de lui rendre sa liberté lorsqu’il aura capturé les Brittle – morts ou vifs. Alors que les deux hommes pistent les dangereux criminels, Django n’oublie pas que son seul but est de retrouver Broomhilda, sa femme, dont il fut séparé à cause du commerce des esclaves… Lorsque Django et Schultz arrivent dans l’immense plantation du puissant Calvin Candie, ils éveillent les soupçons de Stephen, un esclave qui sert Candie et a toute sa confiance. Le moindre de leurs mouvements est désormais épié par une dangereuse organisation de plus en plus proche… Si Django et Schultz veulent espérer s’enfuir avec Broomhilda, ils vont devoir choisir entre l’indépendance et la solidarité, entre le sacrifice et la survie…", null, "/hodO0759IB5LbzUiiLKB50gT2UO.jpg", new Date(1356393600000L), "Django Unchained", 	8.1, 20551)
                )
        );

        return Response.ok(movieResultsPage).build();
    }

    @GET
    @Path("/person/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchPerson(@PathParam("query") String query) {

        PersonResultsPage personResultsPage = buildPersonResultsPage(
                Arrays.asList(
                        buildBasePerson("/wo2hJpn04vbtmh0B9utCFdsQhxM.jpg", 6193, "Leonardo DiCaprio", 16.849, Arrays.asList(
                                buildMedia(
                                        buildBaseMovie(27205, "/s3TBrRGB1iav7gFOCNx3H31MoES.jpg", Arrays.asList(28, 878, 12), "Inception", "Dom Cobb est un voleur expérimenté, le meilleur dans l'art dangereux de l'extraction, voler les secrets les plus intimes enfouis au plus profond du subconscient durant une phase de rêve, lorsque l'esprit est le plus vulnérable. Les capacités de Cobb ont fait des envieux dans le monde tourmenté de l'espionnage industriel alors qu'il devient fugitif en perdant tout ce qu'il a un jour aimé. Une chance de se racheter lui est alors offerte. Une ultime mission grâce à laquelle il pourrait retrouver sa vie passée mais uniquement s'il parvient à accomplir l'impossible inception.", null, "/aej3LRUga5rhgkmRP6XMFw3ejbl.jpg", new Date(1279152000000L), "Inception", 8.3, 28982),
                                        null,
                                        com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                                ),
                                buildMedia(
                                        buildBaseMovie(68718, "/lVcI3MOtumvEbOdS1Og7QoV6Lfc.jpg", Arrays.asList(18, 37), "Django Unchained", "Dans le sud des États‐Unis, deux ans avant la guerre de Sécession, le Dr King Schultz, un chasseur de primes allemand, fait l’acquisition de Django, un esclave qui peut l’aider à traquer les frères Brittle, les meurtriers qu’il recherche. Schultz promet à Django de lui rendre sa liberté lorsqu’il aura capturé les Brittle – morts ou vifs. Alors que les deux hommes pistent les dangereux criminels, Django n’oublie pas que son seul but est de retrouver Broomhilda, sa femme, dont il fut séparé à cause du commerce des esclaves… Lorsque Django et Schultz arrivent dans l’immense plantation du puissant Calvin Candie, ils éveillent les soupçons de Stephen, un esclave qui sert Candie et a toute sa confiance. Le moindre de leurs mouvements est désormais épié par une dangereuse organisation de plus en plus proche… Si Django et Schultz veulent espérer s’enfuir avec Broomhilda, ils vont devoir choisir entre l’indépendance et la solidarité, entre le sacrifice et la survie…", null, "/hodO0759IB5LbzUiiLKB50gT2UO.jpg", new Date(1356393600000L), "Django Unchained", 	8.1, 20551),
                                        null,
                                        com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                                ))
                        ),
                        buildBasePerson("/dMbd2Rx9ZD5Gl9VXAkGcIKGrAxJ.jpg", 10990, "Emma Watson", 15.448, Arrays.asList(
                                buildMedia(
                                        buildBaseMovie(671, "/hziiv14OpD73u9gAak4XDDfBKa2.jpg", Arrays.asList(12, 14), "Harry Potter and the Philosopher's Stone", "Orphelin, Harry Potter a été recueilli en bas âge par sa tante Pétunia et son oncle Vernon, deux abominables créatures qui, depuis dix ans, prennent un malin plaisir à l’humilier, le houspiller et le malmener. Contraint de se nourrir de restes et de dormir dans un placard infesté d’araignées, le malheureux est en butte à l’hostilité de son cousin Dudley, obèse imbécile qui ne manque pas une occasion de le rouer de coups. L’année de ses 11 ans, Harry ne s’attend pas à recevoir de cadeaux, pourtant cette année là, une lettre mystérieuse va lui parvenir qui va changer son existence…", null, "/fbxQ44VRdM2PVzHSNajUseUteem.jpg", new Date(1005868800000L), "Harry Potter à l'école des sorciers", 7.9, 20199),
                                        null,
                                        com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                                ),
                                buildMedia(
                                        buildBaseMovie(672, "/1stUIsjawROZxjiCMtqqXqgfZWC.jpg", Arrays.asList(12, 14), "Harry Potter and the Chamber of Secrets", "Alors que l’oncle Vernon, la tante Pétunia et son cousin Dudley reçoivent d’importants invités à dîner, Harry Potter est contraint de passer la soirée dans sa chambre. Dobby, un elfe, fait alors son apparition. Il lui annonce que de terribles dangers menacent l’école de Poudlard et qu’il ne doit pas y retourner en septembre. Harry refuse de le croire. Mais sitôt la rentrée des classes effectuée, ce dernier entend une voix malveillante. Celle‐ci lui dit que la redoutable et légendaire Chambre des Secrets est à nouveau ouverte, permettant ainsi à l’héritier de Serpentard de semer le chaos à Poudlard. Les victimes, retrouvées pétrifiées par une force mystérieuse, se succèdent dans les couloirs de l’école, sans que les professeurs – pas même le populaire Gilderoy Lockhart – ne parviennent à endiguer la menace. Aidé de Ron et Hermione, Harry doit agir au plus vite pour sauver Poudlard.", null, 	"/8KpHRokGpiaqEGpjYe0rpywtvUx.jpg", new Date(1037145600000L), "Harry Potter et la Chambre des secrets", 7.7, 16389),
                                        null,
                                        com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                                ))
                        )
                )
        );

        return Response.ok(personResultsPage).build();
    }

    @GET
    @Path("/person/{query}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response researchPersonPage(@PathParam("query") String query, @PathParam("page") Integer page) {

        PersonResultsPage personResultsPage = buildPersonResultsPage(
                Arrays.asList(
                        buildBasePerson("/wo2hJpn04vbtmh0B9utCFdsQhxM.jpg", 6193, "Leonardo DiCaprio", 16.849, Arrays.asList(
                                buildMedia(
                                        buildBaseMovie(27205, "/s3TBrRGB1iav7gFOCNx3H31MoES.jpg", Arrays.asList(28, 878, 12), "Inception", "Dom Cobb est un voleur expérimenté, le meilleur dans l'art dangereux de l'extraction, voler les secrets les plus intimes enfouis au plus profond du subconscient durant une phase de rêve, lorsque l'esprit est le plus vulnérable. Les capacités de Cobb ont fait des envieux dans le monde tourmenté de l'espionnage industriel alors qu'il devient fugitif en perdant tout ce qu'il a un jour aimé. Une chance de se racheter lui est alors offerte. Une ultime mission grâce à laquelle il pourrait retrouver sa vie passée mais uniquement s'il parvient à accomplir l'impossible inception.", null, "/aej3LRUga5rhgkmRP6XMFw3ejbl.jpg", new Date(1279152000000L), "Inception", 8.3, 28982),
                                        null,
                                        com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                                ),
                                buildMedia(
                                        buildBaseMovie(68718, "/lVcI3MOtumvEbOdS1Og7QoV6Lfc.jpg", Arrays.asList(18, 37), "Django Unchained", "Dans le sud des États‐Unis, deux ans avant la guerre de Sécession, le Dr King Schultz, un chasseur de primes allemand, fait l’acquisition de Django, un esclave qui peut l’aider à traquer les frères Brittle, les meurtriers qu’il recherche. Schultz promet à Django de lui rendre sa liberté lorsqu’il aura capturé les Brittle – morts ou vifs. Alors que les deux hommes pistent les dangereux criminels, Django n’oublie pas que son seul but est de retrouver Broomhilda, sa femme, dont il fut séparé à cause du commerce des esclaves… Lorsque Django et Schultz arrivent dans l’immense plantation du puissant Calvin Candie, ils éveillent les soupçons de Stephen, un esclave qui sert Candie et a toute sa confiance. Le moindre de leurs mouvements est désormais épié par une dangereuse organisation de plus en plus proche… Si Django et Schultz veulent espérer s’enfuir avec Broomhilda, ils vont devoir choisir entre l’indépendance et la solidarité, entre le sacrifice et la survie…", null, "/hodO0759IB5LbzUiiLKB50gT2UO.jpg", new Date(1356393600000L), "Django Unchained", 	8.1, 20551),
                                        null,
                                        com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                                ))
                        ),
                        buildBasePerson("/dMbd2Rx9ZD5Gl9VXAkGcIKGrAxJ.jpg", 10990, "Emma Watson", 15.448, Arrays.asList(
                                buildMedia(
                                        buildBaseMovie(671, "/hziiv14OpD73u9gAak4XDDfBKa2.jpg", Arrays.asList(12, 14), "Harry Potter and the Philosopher's Stone", "Orphelin, Harry Potter a été recueilli en bas âge par sa tante Pétunia et son oncle Vernon, deux abominables créatures qui, depuis dix ans, prennent un malin plaisir à l’humilier, le houspiller et le malmener. Contraint de se nourrir de restes et de dormir dans un placard infesté d’araignées, le malheureux est en butte à l’hostilité de son cousin Dudley, obèse imbécile qui ne manque pas une occasion de le rouer de coups. L’année de ses 11 ans, Harry ne s’attend pas à recevoir de cadeaux, pourtant cette année là, une lettre mystérieuse va lui parvenir qui va changer son existence…", null, "/fbxQ44VRdM2PVzHSNajUseUteem.jpg", new Date(1005868800000L), "Harry Potter à l'école des sorciers", 7.9, 20199),
                                        null,
                                        com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                                ),
                                buildMedia(
                                        buildBaseMovie(672, "/1stUIsjawROZxjiCMtqqXqgfZWC.jpg", Arrays.asList(12, 14), "Harry Potter and the Chamber of Secrets", "Alors que l’oncle Vernon, la tante Pétunia et son cousin Dudley reçoivent d’importants invités à dîner, Harry Potter est contraint de passer la soirée dans sa chambre. Dobby, un elfe, fait alors son apparition. Il lui annonce que de terribles dangers menacent l’école de Poudlard et qu’il ne doit pas y retourner en septembre. Harry refuse de le croire. Mais sitôt la rentrée des classes effectuée, ce dernier entend une voix malveillante. Celle‐ci lui dit que la redoutable et légendaire Chambre des Secrets est à nouveau ouverte, permettant ainsi à l’héritier de Serpentard de semer le chaos à Poudlard. Les victimes, retrouvées pétrifiées par une force mystérieuse, se succèdent dans les couloirs de l’école, sans que les professeurs – pas même le populaire Gilderoy Lockhart – ne parviennent à endiguer la menace. Aidé de Ron et Hermione, Harry doit agir au plus vite pour sauver Poudlard.", null, 	"/8KpHRokGpiaqEGpjYe0rpywtvUx.jpg", new Date(1037145600000L), "Harry Potter et la Chambre des secrets", 7.7, 16389),
                                        null,
                                        com.uwetrottmann.tmdb2.enumerations.MediaType.MOVIE
                                ))
                        )
                )
        );

        return Response.ok(personResultsPage).build();
    }

    private BaseCompany buildBaseCompany(Integer id, String name){
        BaseCompany baseCompany = new BaseCompany();
        baseCompany.id = id;
        baseCompany.name = name;
        baseCompany.logo_path = null;

        return baseCompany;
    }

    private CompanyResultsPage buildCompanyResultsPage(List<BaseCompany> results) {
        CompanyResultsPage companyResultsPage = new CompanyResultsPage();
        companyResultsPage.id = null;
        companyResultsPage.page = 1;
        companyResultsPage.total_pages = 4;
        companyResultsPage.total_results = 70;
        companyResultsPage.results = results;

        return companyResultsPage;
    }

    private BaseCollection buildBaseCollection(Integer id, String backdrop_path, String name, String poster_path){
        BaseCollection baseCollection = new BaseCollection();
        baseCollection.id = id;
        baseCollection.backdrop_path = backdrop_path;
        baseCollection.name = name;
        baseCollection.poster_path = poster_path;

        return baseCollection;
    }

    private CollectionResultsPage buildCollectionResultsPage(List<BaseCollection> results) {
        CollectionResultsPage collectionResultsPage = new CollectionResultsPage();
        collectionResultsPage.id = null;
        collectionResultsPage.page = 1;
        collectionResultsPage.total_pages = 1;
        collectionResultsPage.total_results = 3;
        collectionResultsPage.results = results;

        return collectionResultsPage;
    }

    private BaseKeyword buildBaseKeyword(Integer id, String name){
        BaseKeyword baseKeyword = new BaseKeyword();
        baseKeyword.id = id;
        baseKeyword.name = name;

        return baseKeyword;
    }

    private KeywordResultsPage buildKeywordResultsPage(List<BaseKeyword> results) {
        KeywordResultsPage keywordResultsPage = new KeywordResultsPage();
        keywordResultsPage.id = null;
        keywordResultsPage.page = 1;
        keywordResultsPage.total_pages = 2;
        keywordResultsPage.total_results = 21;
        keywordResultsPage.results = results;

        return keywordResultsPage;
    }

    private Media buildMedia(BaseMovie movie, BasePerson person, com.uwetrottmann.tmdb2.enumerations.MediaType media_type){
        Media media = new Media();
        media.tvShow = null;
        media.movie = movie;
        media.person = person;
        media.media_type = media_type;

        return media;
    }

    private MediaResultsPage buildMediaResultsPage(List<Media> results) {
        MediaResultsPage mediaResultsPage = new MediaResultsPage();
        mediaResultsPage.id = null;
        mediaResultsPage.page = 1;
        mediaResultsPage.total_pages = 1;
        mediaResultsPage.total_results = 2;
        mediaResultsPage.results = results;

        return mediaResultsPage;
    }

    private BaseMovie buildBaseMovie(
            Integer id,
            String backdrop_path,
            List<Integer> genre_ids,
            String original_title,
            String overview,
            Double popularity,
            String poster_path,
            Date release_date,
            String title,
            Double vote_average,
            Integer vote_count
    ){
        BaseMovie baseMovie = new BaseMovie();
        baseMovie.id = id;
        baseMovie.adult = false;
        baseMovie.backdrop_path = backdrop_path;
        baseMovie.genres = null;
        baseMovie.genre_ids = genre_ids;
        baseMovie.original_title = original_title;
        baseMovie.original_language = "en";
        baseMovie.overview = overview;
        baseMovie.popularity = popularity;
        baseMovie.poster_path = poster_path;
        baseMovie.release_date = release_date;
        baseMovie.title = title;
        baseMovie.vote_average = vote_average;
        baseMovie.vote_count = vote_count;
        baseMovie.media_type = "movie";

        return baseMovie;
    }

    private MovieResultsPage buildMovieResultsPage(List<BaseMovie> results) {
        MovieResultsPage movieResultsPage = new MovieResultsPage();
        movieResultsPage.id = null;
        movieResultsPage.page = 1;
        movieResultsPage.total_pages = 1;
        movieResultsPage.total_results = 2;
        movieResultsPage.results = results;

        return movieResultsPage;
    }

    private BasePerson buildBasePerson(
            String profile_path,
            Integer id,
            String name,
            Double popularity,
            List<Media> known_for
    ){
        BasePerson basePerson = new BasePerson();
        basePerson.profile_path = profile_path;
        basePerson.adult = false;
        basePerson.id = id;
        basePerson.name = name;
        basePerson.popularity = popularity;
        basePerson.known_for = known_for;

        return basePerson;
    }

    private PersonResultsPage buildPersonResultsPage(List<BasePerson> results) {
        PersonResultsPage personResultsPage = new PersonResultsPage();
        personResultsPage.id = null;
        personResultsPage.page = 1;
        personResultsPage.total_pages = 1;
        personResultsPage.total_results = 2;
        personResultsPage.results = results;

        return personResultsPage;
    }
}
