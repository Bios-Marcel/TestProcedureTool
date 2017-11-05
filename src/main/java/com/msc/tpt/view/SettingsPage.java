package com.msc.tpt.view;

import javafx.scene.Node;

public class SettingsPage
{
	private final Node		pageNode;
	private final String	title;

	public SettingsPage(final Node pageNode, final String title)
	{
		super();
		this.pageNode = pageNode;
		this.title = title;
	}

	public Node getPageNode()
	{
		return pageNode;
	}

	public String getTitle()
	{
		return title;
	}
}
