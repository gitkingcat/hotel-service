import java.util.stream.IntStream

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

import org.springframework.web.context.WebApplicationContext

import com.ofg.microservice.hotel_service.HotelServiceApplication
import com.ofg.microservice.hotel_service.controller.HotelServiceExceptionHadnler
import com.ofg.microservice.hotel_service.controller.ReservationController
import com.ofg.microservice.hotel_service.exceptions.IncorrectDatesException
import com.ofg.microservice.hotel_service.exceptions.MalformedDatesException
import com.ofg.microservice.hotel_service.exceptions.ReservationDoesNotExists
import com.ofg.microservice.hotel_service.exceptions.ReservationNotPossibleException
import com.ofg.microservice.hotel_service.exceptions.RoomNotAvailableException
import com.ofg.microservice.hotel_service.repository.ReservationRepository
import com.ofg.microservice.hotel_service.repository.RoomRepository
import com.ofg.microservice.hotel_service.models.ReservationDTO
import com.ofg.microservice.hotel_service.repository.entity.ReservationBuilder
import com.ofg.microservice.hotel_service.service.RoomService
import com.ofg.microservice.hotel_service.validator.DataValidator

import jakarta.annotation.Resource
import jakarta.transaction.Transactional
import spock.lang.Specification

@SpringBootTest(classes = HotelServiceApplication.class)
@ActiveProfiles('test')
@Transactional
class RoomServiceIntegrationSpec extends Specification {

    static final String START_DATE = '2023-07-15'

    static final String END_DATE = '2023-07-20'

    @Resource
    ReservationRepository reservationRepository

    @Resource
    RoomRepository roomRepository

    RoomService roomService

    DataValidator dataValidator

    ReservationBuilder reservationBuilder

    ReservationController reservationController

    ReservationDTO reservationDTO = new ReservationDTO(START_DATE, END_DATE)

    void setup() {
        dataValidator = new DataValidator()
        reservationBuilder = new ReservationBuilder()
        roomService = new RoomService(reservationRepository, roomRepository, dataValidator, reservationBuilder)
        reservationController = new ReservationController(roomService)
    }

    void 'checkAvailability returns true when room is available'() {
        when:
            ResponseEntity<String> response = reservationController.checkAvailability(START_DATE, END_DATE)
        then:
            response.statusCode == HttpStatus.OK
            response.body == 'Room is available.'
    }

    void 'checkAvailability throws exception when no rooms available'() {
        when:
            IntStream.range(0, 10).forEach(i -> roomService.reserveRoom(reservationDTO));
            reservationController.checkAvailability(START_DATE, END_DATE)
        then:
            thrown(RoomNotAvailableException.class)
    }

    void 'checkAvailability throws exception when dates are malformed'() {
        when:
            reservationController.checkAvailability("123-123-123", END_DATE)
        then:
            thrown(MalformedDatesException.class)
    }

    void 'checkAvailability throws exception when end date is before start date'() {
        when:
            reservationController.checkAvailability(END_DATE, START_DATE)
        then:
            thrown(IncorrectDatesException.class)
    }

    void 'checkAvailability throws exception when end date is before start date'() {
        when:
            reservationController.checkAvailability(END_DATE, START_DATE)
        then:
            thrown(IncorrectDatesException.class)
    }

    void 'reserveRoom returns reservation ID when successful'() {
        when:
            ResponseEntity<String> response = reservationController.reserveRoom(reservationDTO)
        then:
            response.statusCode == HttpStatus.OK
            response.body.startsWith('Reservation successful. Reservation ID:')
    }

    void 'reserveRoom returns throws exception when there are no rooms available'() {
        given:
            IntStream.range(0, 10).forEach(i -> roomService.reserveRoom(reservationDTO));
        when:
            reservationController.reserveRoom(reservationDTO)
        then:
            thrown(ReservationNotPossibleException.class)

    }

    void 'reserveRoom throws exception when dates are malformed'() {
        when:
            reservationController.reserveRoom(new ReservationDTO("123-13",END_DATE))
        then:
            thrown(MalformedDatesException.class)
    }

    void 'reserveRoom throws exception when end date is before start date'() {
        when:
            reservationController.reserveRoom(new ReservationDTO(END_DATE,START_DATE))
        then:
            thrown(IncorrectDatesException.class)
    }

    void 'reserveRoom throws exception when end date is the same as start date'() {
        when:
            reservationController.reserveRoom(new ReservationDTO(END_DATE,END_DATE))
        then:
            thrown(IncorrectDatesException.class)
    }

    void 'cancelReservation successfully cancels reservation'() {
        given:
            Long roomNr = roomService.reserveRoom(reservationDTO)
        when:
            roomService.cancelReservation(String.valueOf(roomNr))
        then:
            !reservationRepository.findById(roomNr).isPresent()
    }

    void 'cancelReservation successfully cancels reservation'() {
        when:
            roomService.cancelReservation('1')
        then:
            thrown(ReservationDoesNotExists.class)
    }
}
