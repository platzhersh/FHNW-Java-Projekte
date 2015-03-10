


public class Message {
		public String from;
		public String[] to;
		public String subject;
		public String body;
		
		/* A predicate for a cheap filtering */
		public static class BodyContains implements SimpleFilter.Predicate<Message> {
			public final String needle;
			public BodyContains(String needle) { this.needle = needle; }
			public boolean apply(Message m) { return m.body.contains(needle); }
		}
		
		/* A predicate for a cheap filtering */
		public static class BodyOrSubjectContains implements SimpleFilter.Predicate<Message> {
			public final String needle;
			public BodyOrSubjectContains(String needle) { this.needle = needle; }
			public boolean apply(Message m) { return m.body.contains(needle) || m.subject.contains(needle); }
		}
}
