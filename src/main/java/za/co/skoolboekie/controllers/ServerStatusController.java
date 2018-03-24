package za.co.skoolboekie.controllers;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.skoolboekie.dto.MigrationDTO;
import za.co.skoolboekie.dto.ServerStatusDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ryan on 2/4/2018.
 */

@Slf4j
@RestController
public class ServerStatusController {
    @Autowired
    private HealthCheckRegistry healthCheckRegistry;

    @Autowired
    private MetricRegistry metricRegistry;

    @Autowired
    private Flyway flyway;

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServerStatusDTO> ping() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowAsString = now.format(formatter);

        ServerStatusDTO serverStatusDTO = new ServerStatusDTO();
        serverStatusDTO.setNow(nowAsString);
        serverStatusDTO.setMessage("The service is alive!!");

        MigrationInfo[] migrations = getAllMigrations();

        if (migrations.length > 0) {
            MigrationInfo migrationInfo = Arrays.stream(migrations).reduce((a, b) -> b).orElse(null);
            if (migrationInfo != null) {
                String version = migrationInfo.getVersion().getVersion();
                ZoneId defaultZoneId = ZoneId.systemDefault();
                Instant instant = migrationInfo.getInstalledOn().toInstant();
                String dateOfLastMigration = instant.atZone(defaultZoneId).toLocalDateTime().format(formatter);
                String migrationMessage = migrationInfo.getDescription();
                serverStatusDTO.setDbVersion(new MigrationDTO(version, dateOfLastMigration, migrationMessage));
            }

        } else {
            log.debug("NO DB version info. This might be a bug");
        }

        return new ResponseEntity<>(serverStatusDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HealthMetrics> health() {
        Map<String, HealthCheck.Result> healthChecks = healthCheckRegistry.runHealthChecks();
        Map<String, Metric> metrics = metricRegistry.getMetrics();
        HealthMetrics healthMetrics = new HealthMetrics(healthChecks, metrics);
        return new ResponseEntity<>(healthMetrics, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/migrations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MigrationDTO>> migrations() {
        MigrationInfo[] migrations = getAllMigrations();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<MigrationDTO> migrationDTOS = Arrays.stream(migrations).map(migrationInfo -> {
            String version = migrationInfo.getVersion().getVersion();
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Instant instant = migrationInfo.getInstalledOn().toInstant();
            String dateOfLastMigration = instant.atZone(defaultZoneId).toLocalDateTime().format(formatter);
            String migrationMessage = migrationInfo.getDescription();

            return new MigrationDTO(version, dateOfLastMigration, migrationMessage);

        }).collect(Collectors.toList());

        return new ResponseEntity<>(migrationDTOS, new HttpHeaders(), HttpStatus.OK);
    }

    private MigrationInfo[] getAllMigrations() {
        MigrationInfoService migrationInfoService = flyway.info();
        return migrationInfoService.all();
    }

    @Getter
    private class HealthMetrics {
        Map<String, HealthCheck.Result> healthChecks;
        Map<String, Metric> metrics;

        HealthMetrics(Map<String, HealthCheck.Result> healthChecks, Map<String, Metric> metrics) {
            this.healthChecks = healthChecks;
            this.metrics = metrics;
        }
    }
}
