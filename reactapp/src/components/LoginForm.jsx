import { Button, Card, CardBody, FormControl, FormErrorMessage, FormLabel, Heading, Input, Text, VStack } from '@chakra-ui/react'
import { Field, Form, Formik } from 'formik'
import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import * as Yup from "yup"
import { fetchJSON } from '../helpers/api'
import { toast } from './Toast'
import { useAuth } from '../helpers/useAuth'

const loginFormValidation = Yup.object().shape({
    email: Yup.string().email("Invalid email").required("Please enter your email!"),
    password: Yup.string().required("Please enter your password!")
})

export const LoginForm = () => {
    const { dispatch } = useAuth()
    const navigate = useNavigate()


    const loginFunction = (values) => {
        return fetchJSON("/Account/Login", values, "POST")
    }

    return (
        <Card w={{ md: "70%" }} size={"lg"} zIndex={"9999"}>
            <CardBody>
                <Heading fontSize={"2xl"} mb={3} textAlign={"center"}>Login to Your account</Heading>
                <Formik
                    initialValues={{ email: "", password: "" }}
                    validationSchema={loginFormValidation}
                    onSubmit={(values, actions) => {
                        actions.setSubmitting(true)
                        loginFunction(values).then((data) => {
                            toast({
                                status: "success",
                                title: "Successfully logged in!",
                            })
                            dispatch({
                                type: "set_Token",
                                payload: data.data.token
                            })
                            dispatch({
                                type: "set_user",
                                payload: data.data.user
                            })
                            actions.setSubmitting(false)
                            navigate("/destinations")
                        }).catch(() => {
                            toast({
                                status: "error",
                                title: "Failed to login"
                            })
                        })
                    }}
                >
                    {(props) => (
                        <VStack as={Form}>
                            <Field name='email'>
                                {({ field, form }) => (
                                    <FormControl isInvalid={form.errors.email && form.touched.email}>
                                        <FormLabel fontSize={"sm"} mb={0}>Email</FormLabel>
                                        <Input variant={"filled"} {...field} placeholder='email' />
                                        <FormErrorMessage>{form.errors.email}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Field name='password'>
                                {({ field, form }) => (
                                    <FormControl isInvalid={form.errors.password && form.touched.password}>
                                        <FormLabel fontSize={"sm"} mb={0}>Password</FormLabel>
                                        <Input variant={"filled"} {...field} type='password' placeholder='Password' />
                                        <FormErrorMessage>{form.errors.password}</FormErrorMessage>
                                    </FormControl>
                                )}
                            </Field>
                            <Button
                                mt={4}
                                colorScheme='primary'
                                isLoading={props.isSubmitting}
                                type='submit'
                                w={"full"}
                            >
                                Sign In
                            </Button>
                            <Text fontSize={"sm"}>Don&apos; have an account? <Button colorScheme='blue' as={Link} size={"sm"} to={"/signup"} variant={"link"}>signup</Button></Text>
                        </VStack>
                    )}
                </Formik>
            </CardBody>
        </Card >
    )
}
