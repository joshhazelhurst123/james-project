/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/


package org.apache.james.transport.matchers;

import static org.assertj.core.api.Assertions.assertThat;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.mailet.MailAddress;
import org.apache.mailet.base.test.FakeMail;
import org.apache.mailet.base.test.FakeMailContext;
import org.apache.mailet.base.test.FakeMatcherConfig;
import org.apache.mailet.base.test.MailUtil;
import org.junit.Before;
import org.junit.Test;

public class SubjectStartsWithTest {

    private SubjectStartsWith matcher;
    private MailAddress roger;

    @Before
    public void setup() throws AddressException {
        matcher = new SubjectStartsWith();
        roger = new MailAddress("roger@nasa.org");
    }
    
    @Test
    public void shouldMatchWhenSubjectStartsWithConfiguredValue() throws MessagingException {
        FakeMail mail = FakeMail.builder()
                .recipients(roger)
                .mimeMessage(MailUtil.createMimeMessageWithSubject("testSubject"))
                .build();
        
        FakeMatcherConfig mailetConfig = new FakeMatcherConfig("SubjectIs=test", FakeMailContext.defaultContext());
        
        matcher.init(mailetConfig);

        assertThat(matcher.match(mail)).containsExactly(roger);
    }
    
    @Test
    public void shouldNotMatchWhenSubjectDoesntStartWithConfiguredValue() throws MessagingException {
        FakeMail mail = FakeMail.builder()
                .recipients(roger)
                .mimeMessage(MailUtil.createMimeMessageWithSubject("foobar"))
                .build();
        
        FakeMatcherConfig mailetConfig = new FakeMatcherConfig("SubjectIs=test", FakeMailContext.defaultContext());
        
        matcher.init(mailetConfig);

        assertThat(matcher.match(mail)).isNull();
    }
    

    @Test
    public void shouldNotMatchWhenNoSubject() throws MessagingException {
        FakeMail mail = FakeMail.builder()
                .recipients(roger)
                .mimeMessage(MailUtil.createMimeMessageWithSubject(null))
                .build();
        
        FakeMatcherConfig mailetConfig = new FakeMatcherConfig("SubjectIs=test", FakeMailContext.defaultContext());
        
        matcher.init(mailetConfig);

        assertThat(matcher.match(mail)).isNull();
    }
}
