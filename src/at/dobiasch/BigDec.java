package at.dobiasch;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.PrimitiveManager;
import org.nlogo.api.Syntax;

public class BigDec extends org.nlogo.api.DefaultClassManager {
	private static Map<String,BigDecimal> map= new HashMap<String,BigDecimal>();
	
	private class New  extends DefaultCommand
	{
		public Syntax getSyntax()
		{
			return Syntax.commandSyntax(new int[] {Syntax.StringType(), Syntax.NumberType()});
		}
		
		public void perform(Argument args[], Context context) throws ExtensionException
		{
			try {
				map.put(args[0].getString(), BigDecimal.valueOf(args[1].getIntValue()));
			} catch (LogoException e) {
				e.printStackTrace();
				throw new ExtensionException(e);
			}
		}
	}
	
	private class Scale  extends DefaultCommand
	{
		public Syntax getSyntax()
		{
			return Syntax.commandSyntax(new int[] {Syntax.StringType(), Syntax.NumberType()});
		}
		
		public void perform(Argument args[], Context context) throws ExtensionException
		{
			try {
				BigDecimal x= map.get(args[0].getString()).setScale(args[1].getIntValue());
				map.put(args[0].getString(), x);
			} catch (LogoException e) {
				e.printStackTrace();
				throw new ExtensionException(e);
			}
		}
	}
	
	private class Multiply  extends DefaultCommand
	{
		public Syntax getSyntax()
		{
			return Syntax.commandSyntax(new int[] {Syntax.StringType(), Syntax.StringType(), Syntax.NumberType()});
		}
		
		public void perform(Argument args[], Context context) throws ExtensionException
		{
			try {
				BigDecimal x= map.get(args[1].getString()).multiply(BigDecimal.valueOf(args[2].getDoubleValue()));
				map.put(args[0].getString(), x);
			} catch (LogoException e) {
				e.printStackTrace();
				throw new ExtensionException(e);
			}
		}
	}
	
	private class Divide  extends DefaultCommand
	{
		public Syntax getSyntax()
		{
			return Syntax.commandSyntax(new int[] {Syntax.StringType(), Syntax.StringType(), Syntax.NumberType()});
		}
		
		public void perform(Argument args[], Context context) throws ExtensionException
		{
			try {
				BigDecimal x= map.get(args[1].getString()).divide(BigDecimal.valueOf(args[2].getDoubleValue()));
				map.put(args[0].getString(), x);
			} catch (LogoException e) {
				e.printStackTrace();
				throw new ExtensionException(e);
			}
		}
	}
	
	private class AsString extends DefaultReporter
	{
		public Syntax getSyntax()
		{
			return Syntax.reporterSyntax( new int[] { Syntax.StringType() } , Syntax.StringType() ) ;
		}
		
		public Object report(Argument args[], Context context)
				throws ExtensionException, LogoException
		{
			return map.get(args[0].getString()).toString();
		}
	}

	

	@Override
	public void load(PrimitiveManager manager) throws ExtensionException {
		manager.addPrimitive("new", new New());
		manager.addPrimitive("scale", new Scale());
		manager.addPrimitive("multiply", new Multiply());
		manager.addPrimitive("divide", new Divide());
		manager.addPrimitive("asstring", new AsString());
	}

}
