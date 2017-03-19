package com.verge.repository;

import com.verge.entity.Player;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class PlayerRepositoryITest {

//    @Deployment
//    public static WebArchive createDeployment() {
//
//        WebArchive war = ShrinkWrap.create(WebArchive.class)
//                .addClass(PropertiesHelper.class)
//                .addClass(EntityManagerProducer.class)
//                .addClass(BaseITest.class)
//                .addClass(PlayerRepository.class)
//                .addClass(Player.class)
//                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
//                .addAsResource("properties")
//                .addAsLibraries(resolveDependencies());
//
//        System.out.println(war.toString(true));
//
//        return war;
//    }

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(MavenImporter.class)
                .loadPomFromFile("pom.xml")
                .importBuildOutput()
                .as(WebArchive.class);
    }

    @Inject
    private PlayerRepository playerRepository;

    @Test
    public void getAllPlayers() throws Exception {
        List<Player> players = playerRepository.findAll();
        assertThat(players.size(), equalTo(6));
    }

    @Test
    public void getPlayerById() {
        Player player = playerRepository.findBy(1L);
        assertThat(player.getName(), equalTo("Jimmy Page"));
    }

}
