package com.verge.service;

import com.verge.dto.PlayerInfo;
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
public class PlayerServiceITest {

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(MavenImporter.class)
                .loadPomFromFile("pom.xml")
                .importBuildOutput()
                .as(WebArchive.class);
    }

    @Inject
    private PlayerService playerService;

    @Test
    public void getAllPlayers() {
        List<PlayerInfo> dtos = playerService.getPlayers();
        assertThat(dtos.size(), equalTo(6));
    }

    @Test
    public void getPlayerById() {
        PlayerInfo dto = playerService.getPlayer(1L);
        assertThat(dto.getName(), equalTo("Jimmy Page"));
    }
}
