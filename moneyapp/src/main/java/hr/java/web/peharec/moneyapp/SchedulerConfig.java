package hr.java.web.peharec.moneyapp;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {
	@Bean
	public JobDetail myJobDetail() {
	return
	JobBuilder.newJob(MyJob.class).withIdentity("myJob").storeDurably().build();
	}

	@Bean
	public Trigger testJobTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever();
		return TriggerBuilder.newTrigger().forJob(myJobDetail())
				.withIdentity("myTrigger").withSchedule(scheduleBuilder).build(); 
	}
}