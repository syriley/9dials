/*
 * silvertunnel.org Demo - Java example applications accessing anonymity networks
 * Copyright (C) 2009-2011 silvertunnel.org
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package util;

import java.io.IOException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.nodes.Document;

/**
 * Interface to define a download client.
 *
 * 
 * 
 * {@link http://en.wikipedia.org/wiki/URI_scheme}:
 * 
 *  foo://username:password@example.com:8042/over/there/index.dtb?type=animal;name=ferret#nose
 *  \ /   \_______________/ \_________/ \__/            \___/ \_/ \_____________________/ \__/
 *   |           |               |       |                |    |           |                |
 *   |       userInfo          host     port              |    |         query          ref(fragment)
 *   |    \________________________________/\_____________|____|/
 * protocol(scheme)        |                         |    |    |
 *   |                authority                    path   |    |
 *                                          \_____________|____|________________________/ 
 *                                                        |    |           |
 *                                                        |    |          file
 * 
 * @author hapke
 */
public interface HttpClient {
    public String download(URL source) throws IOException, ClientProtocolException;
    public Document downloadAsDocument(URL source) throws IOException, ClientProtocolException;
}
