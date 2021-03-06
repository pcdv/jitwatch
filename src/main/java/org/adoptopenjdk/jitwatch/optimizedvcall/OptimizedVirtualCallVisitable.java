/*
 * Copyright (c) 2013, 2014 Chris Newland.
 * Licensed under https://github.com/AdoptOpenJDK/jitwatch/blob/master/LICENSE-BSD
 * Instructions: https://github.com/AdoptOpenJDK/jitwatch/wiki
 */
package org.adoptopenjdk.jitwatch.optimizedvcall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.adoptopenjdk.jitwatch.model.IMetaMember;
import org.adoptopenjdk.jitwatch.model.IReadOnlyJITDataModel;
import org.adoptopenjdk.jitwatch.treevisitor.ITreeVisitable;
import org.adoptopenjdk.jitwatch.treevisitor.TreeVisitor;

public class OptimizedVirtualCallVisitable implements ITreeVisitable
{
	private List<OptimizedVirtualCall> optimizedVCallReport = new ArrayList<>();

	public List<OptimizedVirtualCall> buildOptimizedCalleeReport(IReadOnlyJITDataModel model)
	{
		TreeVisitor.walkTree(model, this);

		Collections.sort(optimizedVCallReport, new Comparator<OptimizedVirtualCall>()
		{
			@Override
			public int compare(OptimizedVirtualCall o1, OptimizedVirtualCall o2)
			{
				return o1.getCallingMember().getFullyQualifiedMemberName()
						.compareTo(o2.getCallingMember().getFullyQualifiedMemberName());
			}
		});

		return optimizedVCallReport;
	}

	@Override
	public void reset()
	{
		optimizedVCallReport.clear();
	}

	@Override
	public void visit(IMetaMember mm)
	{
		List<OptimizedVirtualCall> vCallsForMember = OptimizedVirtualCallFinder.findOptimizedCalls(mm);
		
		optimizedVCallReport.addAll(vCallsForMember);
	}
}