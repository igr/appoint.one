package domain.doctor

import DateTime
import domain.timeslot.Timeslot
import domain.timeslot.TimeslotId
import domain.user.UserId

typealias _FindDoctorById = (DoctorId) -> Doctor?

typealias _FindExistingDoctorById = (DoctorId) -> Doctor

typealias _UpdateDoctorData = (DoctorId, DoctorData) -> Unit

typealias _AssertDoctorIsUser = (DoctorId, UserId?) -> Unit

typealias _ListDoctorsOrdered = () -> List<Doctor>

typealias _ListDoctorsTimeslots = (DoctorId) -> List<Timeslot>

typealias _BindTimeslotsToDoctor = (DoctorId, List<DateTime>) -> List<TimeslotId>

//class DoctorContext(id: DoctorId) : Context<DoctorId>(id) {
//
//	val updateDoctorData = curry(UpdateDoctorData)
//
//}
