package com.metro;

import com.metro.model.Station;
import com.metro.repository.StationRepository;
import com.metro.service.StationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * StationService 單元測試
 */
@ExtendWith(MockitoExtension.class)
class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationService stationService;

    private Station testStation;

    @BeforeEach
    void setUp() {
        testStation = new Station("R3", "中央車站", "紅線");
        testStation.setId(1L);
    }

    @Test
    void testGetAllStations() {
        // Arrange
        List<Station> stations = Arrays.asList(testStation);
        when(stationRepository.findAll()).thenReturn(stations);

        // Act
        List<Station> result = stationService.getAllStations();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("中央車站", result.get(0).getName());
        verify(stationRepository, times(1)).findAll();
    }

    @Test
    void testGetStationById_Success() {
        // Arrange
        when(stationRepository.findById(1L)).thenReturn(Optional.of(testStation));

        // Act
        Station result = stationService.getStationById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("中央車站", result.getName());
        assertEquals("R3", result.getCode());
        verify(stationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStationById_NotFound() {
        // Arrange
        when(stationRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            stationService.getStationById(999L);
        });
        verify(stationRepository, times(1)).findById(999L);
    }

    @SuppressWarnings("null")
    @Test
    void testCreateStation_Success() {
        // Arrange
        when(stationRepository.findByCode("R3")).thenReturn(Optional.empty());
        when(stationRepository.save(any(Station.class))).thenReturn(testStation);

        // Act
        Station result = stationService.createStation(testStation);

        // Assert
        assertNotNull(result);
        assertEquals("中央車站", result.getName());
        verify(stationRepository, times(1)).findByCode("R3");
        verify(stationRepository, times(1)).save(any());
    }

    @SuppressWarnings("null")
    @Test
    void testCreateStation_DuplicateCode() {
        // Arrange
        when(stationRepository.findByCode("R3")).thenReturn(Optional.of(testStation));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            stationService.createStation(testStation);
        });
        verify(stationRepository, times(1)).findByCode("R3");
        verify(stationRepository, never()).save(any());
    }

    @Test
    void testDeleteStation_Success() {
        // Arrange
        when(stationRepository.existsById(1L)).thenReturn(true);
        doNothing().when(stationRepository).deleteById(1L);

        // Act
        stationService.deleteStation(1L);

        // Assert
        verify(stationRepository, times(1)).existsById(1L);
        verify(stationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteStation_NotFound() {
        // Arrange
        when(stationRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            stationService.deleteStation(999L);
        });
        verify(stationRepository, times(1)).existsById(999L);
        verify(stationRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetStationsByLine() {
        // Arrange
        List<Station> redLineStations = Arrays.asList(testStation);
        when(stationRepository.findByLine("紅線")).thenReturn(redLineStations);

        // Act
        List<Station> result = stationService.getStationsByLine("紅線");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("紅線", result.get(0).getLine());
        verify(stationRepository, times(1)).findByLine("紅線");
    }
}

// Made with Bob
