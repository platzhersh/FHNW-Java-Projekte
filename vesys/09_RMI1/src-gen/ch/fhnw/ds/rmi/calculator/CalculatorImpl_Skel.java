// Skeleton class generated by rmic, do not edit.
// Contents subject to change without notice.

package ch.fhnw.ds.rmi.calculator;

@SuppressWarnings("deprecation")
public final class CalculatorImpl_Skel implements java.rmi.server.Skeleton {
	private static final java.rmi.server.Operation[] operations = {
			new java.rmi.server.Operation("long add(long, long)"),
			new java.rmi.server.Operation("long div(long, long)"),
			new java.rmi.server.Operation("long mul(long, long)"),
			new java.rmi.server.Operation("long sub(long, long)") };

	private static final long interfaceHash = 2829799981544659448L;

	public java.rmi.server.Operation[] getOperations() {
		return (java.rmi.server.Operation[]) operations.clone();
	}

	public void dispatch(java.rmi.Remote obj, java.rmi.server.RemoteCall call,
			int opnum, long hash) throws java.lang.Exception {
		if (opnum < 0) {
			if (hash == 8040560279107955721L) {
				opnum = 0;
			} else if (hash == 5180157848515624758L) {
				opnum = 1;
			} else if (hash == -4927388321647852427L) {
				opnum = 2;
			} else if (hash == -381845905804201651L) {
				opnum = 3;
			} else {
				throw new java.rmi.UnmarshalException("invalid method hash");
			}
		} else {
			if (hash != interfaceHash)
				throw new java.rmi.server.SkeletonMismatchException(
						"interface hash mismatch");
		}

		ch.fhnw.ds.rmi.calculator.CalculatorImpl server = (ch.fhnw.ds.rmi.calculator.CalculatorImpl) obj;
		switch (opnum) {
		case 0: // add(long, long)
		{
			long $param_long_1;
			long $param_long_2;
			try {
				java.io.ObjectInput in = call.getInputStream();
				$param_long_1 = in.readLong();
				$param_long_2 = in.readLong();
			} catch (java.io.IOException e) {
				throw new java.rmi.UnmarshalException(
						"error unmarshalling arguments", e);
			} finally {
				call.releaseInputStream();
			}
			long $result = server.add($param_long_1, $param_long_2);
			try {
				java.io.ObjectOutput out = call.getResultStream(true);
				out.writeLong($result);
			} catch (java.io.IOException e) {
				throw new java.rmi.MarshalException("error marshalling return",
						e);
			}
			break;
		}

		case 1: // div(long, long)
		{
			long $param_long_1;
			long $param_long_2;
			try {
				java.io.ObjectInput in = call.getInputStream();
				$param_long_1 = in.readLong();
				$param_long_2 = in.readLong();
			} catch (java.io.IOException e) {
				throw new java.rmi.UnmarshalException(
						"error unmarshalling arguments", e);
			} finally {
				call.releaseInputStream();
			}
			long $result = server.div($param_long_1, $param_long_2);
			try {
				java.io.ObjectOutput out = call.getResultStream(true);
				out.writeLong($result);
			} catch (java.io.IOException e) {
				throw new java.rmi.MarshalException("error marshalling return",
						e);
			}
			break;
		}

		case 2: // mul(long, long)
		{
			long $param_long_1;
			long $param_long_2;
			try {
				java.io.ObjectInput in = call.getInputStream();
				$param_long_1 = in.readLong();
				$param_long_2 = in.readLong();
			} catch (java.io.IOException e) {
				throw new java.rmi.UnmarshalException(
						"error unmarshalling arguments", e);
			} finally {
				call.releaseInputStream();
			}
			long $result = server.mul($param_long_1, $param_long_2);
			try {
				java.io.ObjectOutput out = call.getResultStream(true);
				out.writeLong($result);
			} catch (java.io.IOException e) {
				throw new java.rmi.MarshalException("error marshalling return",
						e);
			}
			break;
		}

		case 3: // sub(long, long)
		{
			long $param_long_1;
			long $param_long_2;
			try {
				java.io.ObjectInput in = call.getInputStream();
				$param_long_1 = in.readLong();
				$param_long_2 = in.readLong();
			} catch (java.io.IOException e) {
				throw new java.rmi.UnmarshalException(
						"error unmarshalling arguments", e);
			} finally {
				call.releaseInputStream();
			}
			long $result = server.sub($param_long_1, $param_long_2);
			try {
				java.io.ObjectOutput out = call.getResultStream(true);
				out.writeLong($result);
			} catch (java.io.IOException e) {
				throw new java.rmi.MarshalException("error marshalling return",
						e);
			}
			break;
		}

		default:
			throw new java.rmi.UnmarshalException("invalid method number");
		}
	}
}
