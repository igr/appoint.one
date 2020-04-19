package domain.timeslot

import DateTime
import domain.evaluation.EvaluationData
import domain.evaluation.EvaluationId
import domain.user.UserId

typealias _ListTimeslotsForIds = (List<TimeslotId>) -> List<Timeslot>

typealias _FindTimeslotById = (TimeslotId) -> Timeslot?

typealias _DeleteTimeslot = (TimeslotId) -> Unit

typealias _ActivateTimeslotIfReserved = (TimeslotId) -> Unit

typealias _CancelTimeslotIfReserved = (TimeslotId) -> Unit

typealias _ReserveTimeslotIfNew = (TimeslotId) -> Unit

typealias _MarkTimeslotAsDone = (TimeslotId, EvaluationData) -> EvaluationId

typealias _AssertTimeslotIsOwnedByUser = (TimeslotId, UserId?) -> Unit

typealias _CountAvailableTimeslots = () -> Long

typealias _CalculateTimeslotUsageStats = () -> List<TimeslotStatusCount>

typealias _DetermineNextAvailableTimeslots = (Int, DateTime) -> List<TimeslotAndDoctor>

