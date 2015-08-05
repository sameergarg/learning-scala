package metrics

import com.kenshoo.play.metrics.MetricsFilter
import play.api.GlobalSettings
import play.api.mvc.WithFilters

class Global extends GlobalSettings {

}
object Global extends WithFilters(MetricsFilter)
