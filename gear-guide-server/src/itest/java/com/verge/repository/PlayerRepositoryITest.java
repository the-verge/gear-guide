package com.verge.repository;

import com.verge.entity.Player;
import com.verge.test.common.BaseITest;
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
public class PlayerRepositoryITest extends BaseITest {

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

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(MavenImporter.class)
                .loadPomFromFile("pom.xml")
                .importBuildOutput()
                .as(WebArchive.class);
    }

    @Inject
    private PlayerRepository playerRepository;

    @Test
    //@RunAsClient
    public void testGetAllPlayers() throws Exception {
        List<Player> players = playerRepository.findAll();
        assertThat(players.size(), equalTo(4));
    }

}
